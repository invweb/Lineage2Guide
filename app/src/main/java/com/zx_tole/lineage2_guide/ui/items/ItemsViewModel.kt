package com.zx_tole.lineage2_guide.ui.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zx_tole.lineage2_guide.domain.model.Item
import com.zx_tole.lineage2_guide.domain.model.ItemsFilter
import com.zx_tole.lineage2_guide.domain.usecase.GetItemsUseCase
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
    private val getItemsUseCase: GetItemsUseCase
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
                getItemsUseCase.invoke(ItemsFilter())
                    .catch { e ->
                        Timber.e(e, "Error loading items")
                    }
                    .collect { items ->
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
        loadItems()
    }
}
