package com.zx_tole.lineage2_guide.ui.skills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zx_tole.lineage2_guide.domain.model.Skill
import com.zx_tole.lineage2_guide.domain.usecase.GetSkillsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

data class SkillsUiState(
    val skills: List<Skill> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val filterClass: String? = null
)

class SkillsViewModel(
    private val getSkillsUseCase: GetSkillsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SkillsUiState(isLoading = true))
    val uiState: StateFlow<SkillsUiState> = _uiState.asStateFlow()

    init {
        loadSkills()
    }

    private fun loadSkills() {
        viewModelScope.launch {
            try {
                getSkillsUseCase.invoke()
                    .catch { e ->
                        Timber.e(e, "Error loading skills")
                        _uiState.update { it.copy(isLoading = false, error = e.message) }
                    }
                    .collect { skills ->
                        _uiState.update {
                            it.copy(
                                skills = skills,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
            } catch (e: Exception) {
                Timber.e(e, "Error in skills flow")
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            try {
                getSkillsUseCase.invoke()
                    .catch { e ->
                        Timber.e(e, "Error refreshing skills")
                    }
                    .collect { skills ->
                        _uiState.update {
                            it.copy(
                                skills = skills,
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

    fun filterByClass(classRestriction: String?) {
        viewModelScope.launch {
            _uiState.update { it.copy(filterClass = classRestriction, isLoading = true) }
            try {
                getSkillsUseCase.invoke(classRestriction = classRestriction)
                    .catch { e ->
                        Timber.e(e, "Error filtering skills")
                    }
                    .collect { skills ->
                        _uiState.update {
                            it.copy(
                                skills = skills,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
            } catch (e: Exception) {
                Timber.e(e, "Error in filter")
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
