package com.zx_tole.lineage2_guide.ui.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zx_tole.lineage2_guide.domain.model.GameClass
import com.zx_tole.lineage2_guide.domain.usecase.GetClassesUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

data class ClassesUiState(
    val classes: List<GameClass> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val searchQuery: String? = null
)

class ClassesViewModel(
    private val getClassesUseCase: GetClassesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClassesUiState(isLoading = true))
    val uiState: StateFlow<ClassesUiState> = _uiState.asStateFlow()

    init {
        loadClasses()
    }

    private fun loadClasses() {
        viewModelScope.launch {
            try {
                getClassesUseCase.invoke()
                    .catch { e ->
                        Timber.e(e, "Error loading classes")
                        _uiState.update { it.copy(isLoading = false, error = e.message) }
                    }
                    .collect { classes ->
                        _uiState.update {
                            it.copy(
                                classes = classes,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
            } catch (e: Exception) {
                Timber.e(e, "Error in classes flow")
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            try {
                getClassesUseCase.invoke()
                    .catch { e ->
                        Timber.e(e, "Error refreshing classes")
                    }
                    .collect { classes ->
                        _uiState.update {
                            it.copy(
                                classes = classes,
                                isRefreshing = false,
                                error = null
                            )
                        }
                    }
            } catch (e: Exception) {
                Timber.e(e, "Error in refresh")
                _uiState.update { it.copy(isRefreshing = false, error = e.message) }
            }
        }
    }

    fun search(query: String?) {
        viewModelScope.launch {
            _uiState.update { it.copy(searchQuery = query, isLoading = true) }
            try {
                getClassesUseCase.invoke(searchQuery = query)
                    .catch { e ->
                        Timber.e(e, "Error searching classes")
                    }
                    .collect { classes ->
                        _uiState.update {
                            it.copy(
                                classes = classes,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
            } catch (e: Exception) {
                Timber.e(e, "Error in search")
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
