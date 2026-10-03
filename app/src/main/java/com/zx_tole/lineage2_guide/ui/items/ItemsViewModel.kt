package com.zx_tole.lineage2_guide.ui.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zx_tole.lineage2_guide.domain.model.*
import com.zx_tole.lineage2_guide.domain.usecase.GetItemsUseCase
import com.zx_tole.lineage2_guide.domain.usecase.GetTotalCountUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

data class PaginationState(
    val filter: ItemsFilter,
    val page: Int,
    val total: Int,
    val isLoadingMore: Boolean
)

class ItemsViewModel(
    private val getItemsUseCase: GetItemsUseCase,
    private val getTotalCountUseCase: GetTotalCountUseCase
) : ViewModel() {

    private val searchQueryFlow = MutableStateFlow("")
    private val selectedClassFlow = MutableStateFlow<String?>(null)
    private val selectedLevelRangeFlow = MutableStateFlow<IntRange?>(null)
    private val selectedTypeFlow = MutableStateFlow<String?>(null)
    private val selectedRarityFlow = MutableStateFlow<String?>(null)
    private val selectedLocationFlow = MutableStateFlow<String?>(null)
    private val sortByFlow = MutableStateFlow(SortOption.NAME_ASC)
    private val currentPageFlow = MutableStateFlow(0)
    private val totalItemsFlow = MutableStateFlow(0)
    private val isLoadingMoreFlow = MutableStateFlow(false)

    private val filterFlow: Flow<ItemsFilter> = combine(
        searchQueryFlow.debounce(300),
        selectedClassFlow,
        selectedLevelRangeFlow,
        selectedTypeFlow,
        selectedRarityFlow,
        selectedLocationFlow,
        sortByFlow
    ) { values ->
        ItemsFilter(
            classRestriction = values[1] as? String,
            minLevel = (values[2] as? IntRange)?.start,
            maxLevel = (values[2] as? IntRange)?.endInclusive,
            type = values[3] as? String,
            rarity = values[4] as? String,
            location = values[5] as? String,
            searchQuery = (values[0] as String).takeIf { it.isNotBlank() },
            sortBy = values[6] as SortOption
        )
    }

    // Load total count when filters change
    init {
        filterFlow.distinctUntilChanged().onEach { filter ->
            viewModelScope.launch {
                try {
                    val count = getTotalCountUseCase.invoke(filter)
                    totalItemsFlow.value = count
                } catch (e: Exception) {
                    Timber.e(e, "Error fetching total count")
                    totalItemsFlow.value = 0
                }
            }
        }.launchIn(viewModelScope)
    }

    val uiState: StateFlow<ItemsUiState> = filterFlow
        .combine(currentPageFlow) { filter, page ->
            PaginationState(filter, page, totalItemsFlow.value, isLoadingMoreFlow.value)
        }
        .combine(isLoadingMoreFlow) { state, isLoadingMore ->
            state.copy(isLoadingMore = isLoadingMore)
        }
        .flatMapLatest { state ->
            getItemsUseCase.invoke(state.filter.copy(page = state.page))
                .map { items ->
                    val hasMore = (state.page + 1) * state.filter.pageSize < state.total
                    ItemsUiState.Success(
                        items = items,
                        isLoading = false,
                        isLoadingMore = state.isLoadingMore,
                        error = null,
                        filterState = FilterState(
                            selectedClasses = selectedClassFlow.value?.let { setOf(it) } ?: emptySet(),
                            levelRange = selectedLevelRangeFlow.value,
                            selectedTypes = selectedTypeFlow.value?.let { setOf(it) } ?: emptySet(),
                            selectedRarities = selectedRarityFlow.value?.let { setOf(it) } ?: emptySet(),
                            selectedLocations = selectedLocationFlow.value?.let { setOf(it) } ?: emptySet(),
                            searchQuery = searchQueryFlow.value,
                            sortBy = sortByFlow.value
                        ),
                        isRefreshing = false,
                        totalItems = state.total,
                        hasMore = hasMore
                    )
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ItemsUiState.Loading()
        )

    fun onSearchQueryChanged(query: String) {
        searchQueryFlow.value = query
        currentPageFlow.value = 0
    }

    fun onClassSelected(classId: String?) {
        selectedClassFlow.value = classId
        currentPageFlow.value = 0
    }

    fun onLevelRangeChanged(range: IntRange?) {
        selectedLevelRangeFlow.value = range
        currentPageFlow.value = 0
    }

    fun onTypeSelected(type: String?) {
        selectedTypeFlow.value = type
        currentPageFlow.value = 0
    }

    fun onRaritySelected(rarity: String?) {
        selectedRarityFlow.value = rarity
        currentPageFlow.value = 0
    }

    fun onLocationSelected(location: String?) {
        selectedLocationFlow.value = location
        currentPageFlow.value = 0
    }

    fun onSortChanged(sort: SortOption) {
        sortByFlow.value = sort
        currentPageFlow.value = 0
    }

    fun loadMore() {
        if (isLoadingMoreFlow.value) return
        viewModelScope.launch {
            isLoadingMoreFlow.value = true
            currentPageFlow.update { it + 1 }
            kotlinx.coroutines.delay(100)
            isLoadingMoreFlow.value = false
        }
    }

    fun refresh() {
        currentPageFlow.value = 0
        searchQueryFlow.value = searchQueryFlow.value
    }

    fun clearFilters() {
        searchQueryFlow.value = ""
        selectedClassFlow.value = null
        selectedLevelRangeFlow.value = null
        selectedTypeFlow.value = null
        selectedRarityFlow.value = null
        selectedLocationFlow.value = null
        sortByFlow.value = SortOption.NAME_ASC
        currentPageFlow.value = 0
    }
}
