package com.zx_tole.lineage2_guide.ui.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zx_tole.lineage2_guide.domain.model.Item
import com.zx_tole.lineage2_guide.domain.usecase.GetItemsUseCase
import com.zx_tole.lineage2_guide.domain.usecase.GetTotalCountUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

data class ItemDetailUiState(
    val item: Item? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class ItemDetailViewModel(
    private val getItemsUseCase: GetItemsUseCase,
    private val getTotalCountUseCase: GetTotalCountUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemDetailUiState(isLoading = true))
    val uiState: StateFlow<ItemDetailUiState> = _uiState.asStateFlow()

    fun loadItem(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                getItemsUseCase.invoke(com.zx_tole.lineage2_guide.domain.model.ItemsFilter(page = 0))
                    .catch { e ->
                        Timber.e(e, "Error loading item")
                        _uiState.update { it.copy(isLoading = false, error = e.message) }
                    }
                    .collect { items ->
                        val found = items.find { i -> i.id == id }
                        if (found != null) {
                            _uiState.update {
                                it.copy(item = found, isLoading = false, error = null)
                            }
                        } else {
                            _uiState.update {
                                it.copy(isLoading = false, error = "Item not found")
                            }
                        }
                    }
            } catch (e: Exception) {
                Timber.e(e, "Error in item detail")
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
