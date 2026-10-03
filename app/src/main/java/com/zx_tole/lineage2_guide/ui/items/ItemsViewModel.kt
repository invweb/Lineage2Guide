package com.zx_tole.lineage2_guide.ui.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zx_tole.lineage2_guide.domain.model.*
import com.zx_tole.lineage2_guide.domain.usecase.GetItemsUseCase
import kotlinx.coroutines.flow.*
import timber.log.Timber

class ItemsViewModel(
    getItemsUseCase: GetItemsUseCase
) : ViewModel() {

    private val searchQueryFlow = MutableStateFlow("")
    private val selectedClassFlow = MutableStateFlow<String?>(null)
    private val selectedLevelRangeFlow = MutableStateFlow<IntRange?>(null)
    private val selectedTypeFlow = MutableStateFlow<String?>(null)
    private val selectedRarityFlow = MutableStateFlow<String?>(null)
    private val selectedLocationFlow = MutableStateFlow<String?>(null)
    private val sortByFlow = MutableStateFlow(SortOption.NAME_ASC)

    private val getItemsUseCase = getItemsUseCase

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

    val uiState: StateFlow<ItemsUiState> = filterFlow
        .flatMapLatest { filter ->
            getItemsUseCase.invoke(filter)
                .catch { e ->
                    Timber.e(e, "Error in items flow")
                    emit(emptyList())
                }
        }
        .map { items ->
            ItemsUiState.Success(
                items = items,
                isLoading = false,
                error = null,
                filterState = FilterState(
                    selectedClasses = selectedClassFlow.value?.let { setOf(it) } ?: emptySet(),
                    levelRange = selectedLevelRangeFlow.value,
                    selectedTypes = selectedTypeFlow.value?.let { setOf(it) } ?: emptySet(),
                    selectedRarities = selectedRarityFlow.value?.let { setOf(it) } ?: emptySet(),
                    selectedLocations = selectedLocationFlow.value?.let { setOf(it) } ?: emptySet(),
                    searchQuery = searchQueryFlow.value,
                    sortBy = sortByFlow.value
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ItemsUiState.Loading()
        )

    fun onSearchQueryChanged(query: String) {
        searchQueryFlow.value = query
    }

    fun onClassSelected(classId: String?) {
        selectedClassFlow.value = classId
    }

    fun onLevelRangeChanged(range: IntRange?) {
        selectedLevelRangeFlow.value = range
    }

    fun onTypeSelected(type: String?) {
        selectedTypeFlow.value = type
    }

    fun onRaritySelected(rarity: String?) {
        selectedRarityFlow.value = rarity
    }

    fun onLocationSelected(location: String?) {
        selectedLocationFlow.value = location
    }

    fun onSortChanged(sort: SortOption) {
        sortByFlow.value = sort
    }

    fun refresh() {
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
    }
}
