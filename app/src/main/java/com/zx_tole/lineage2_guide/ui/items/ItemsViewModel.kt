package com.zx_tole.lineage2_guide.ui.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zx_tole.lineage2_guide.domain.model.Item
import com.zx_tole.lineage2_guide.domain.model.ItemsFilter
import com.zx_tole.lineage2_guide.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

data class ItemsUiState(
    val items: List<Item> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val isRefreshing: Boolean = false
)

class ItemsViewModel(
    private val itemsRepository: ItemsRepository
) : ViewModel() {

    private val allItems = MutableStateFlow<List<Item>>(emptyList())
    private val searchQueryFlow = MutableStateFlow("")

    private val filteredItems: StateFlow<List<Item>> = searchQueryFlow
        .combine(allItems) { query, items ->
            if (query.isBlank()) {
                items
            } else {
                items.filter { item ->
                    item.name.contains(query, ignoreCase = true) ||
                        item.description?.contains(query, ignoreCase = true) == true
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val uiState: StateFlow<ItemsUiState> = filteredItems
        .map { items ->
            ItemsUiState(
                items = items,
                isLoading = false,
                error = null,
                searchQuery = searchQueryFlow.value,
                isRefreshing = false
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ItemsUiState(isLoading = true)
        )

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            try {
                itemsRepository.getItems(ItemsFilter()).collect { items ->
                    allItems.value = items
                }
            } catch (e: Exception) {
                Timber.e(e, "Error in items load")
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        searchQueryFlow.value = query
    }

    fun refresh() {
        viewModelScope.launch {
            itemsRepository.refreshItems()
            loadItems()
        }
    }
}
