package com.zx_tole.lineage2_guide.ios.viewmodels

import com.zx_tole.lineage2_guide.domain.model.ItemsFilter
import com.zx_tole.lineage2_guide.domain.repository.ItemsRepository
import com.zx_tole.lineage2_guide.ui.items.ItemsUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class IOSItemsViewModel(
    private val itemsRepository: ItemsRepository
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    private val allItems = MutableStateFlow<List<com.zx_tole.lineage2_guide.domain.model.Item>>(emptyList())
    private val searchQueryFlow = MutableStateFlow("")
    
    private val filteredItems: StateFlow<List<com.zx_tole.lineage2_guide.domain.model.Item>> = searchQueryFlow
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
        .stateIn(scope, SharingStarted.WhileSubscribed(5000), emptyList())
    
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
        .stateIn(scope, SharingStarted.WhileSubscribed(5000), ItemsUiState(isLoading = true))
    
    init {
        loadItems()
    }
    
    private fun loadItems() {
        scope.launch {
            try {
                itemsRepository.getItems(ItemsFilter()).collect { items ->
                    allItems.value = items
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
    
    fun onSearchQueryChanged(query: String) {
        searchQueryFlow.value = query
    }
    
    fun refresh() {
        scope.launch {
            itemsRepository.refreshItems()
            loadItems()
        }
    }
}
