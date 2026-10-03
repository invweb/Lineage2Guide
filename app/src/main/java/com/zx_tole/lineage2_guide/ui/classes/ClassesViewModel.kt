package com.zx_tole.lineage2_guide.ui.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zx_tole.lineage2_guide.domain.model.GameClass
import com.zx_tole.lineage2_guide.domain.repository.ClassesRepository
import com.zx_tole.lineage2_guide.domain.usecase.GetClassesUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

data class ClassesUiState(
    val classes: List<GameClass> = emptyList(),
    val allClasses: List<GameClass> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)

class ClassesViewModel(
    private val getClassesUseCase: GetClassesUseCase,
    private val classesRepository: ClassesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClassesUiState(isLoading = true))
    val uiState: StateFlow<ClassesUiState> = _uiState.asStateFlow()

    init {
        loadClasses()
        // Load from server if empty
        viewModelScope.launch {
            val currentClasses = _uiState.value.classes
            if (currentClasses.isEmpty()) {
                Timber.d("Loading classes from server")
                classesRepository.refreshClasses()
                loadClasses()
            }
        }
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
                        Timber.d("Loaded ${classes.size} classes from database")
                        _uiState.update {
                            it.copy(
                                allClasses = classes,
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
                        val query = _uiState.value.searchQuery
                        val filtered = if (query.isNotBlank()) {
                            classes.filter { c ->
                                c.name.contains(query, ignoreCase = true) ||
                                    c.race.contains(query, ignoreCase = true) ||
                                    c.subClasses.any { sub -> sub.contains(query, ignoreCase = true) }
                            }
                        } else {
                            classes
                        }
                        _uiState.update {
                            it.copy(
                                allClasses = classes,
                                classes = filtered,
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

    fun onSearchQueryChanged(query: String) {
        _uiState.update { state ->
            val filtered = if (query.isNotBlank()) {
                state.allClasses.filter { c ->
                    c.name.contains(query, ignoreCase = true) ||
                        c.race.contains(query, ignoreCase = true) ||
                        c.subClasses.any { sub -> sub.contains(query, ignoreCase = true) }
                }
            } else {
                state.allClasses
            }
            state.copy(
                classes = filtered,
                searchQuery = query,
                isLoading = false,
                error = null
            )
        }
    }
}
