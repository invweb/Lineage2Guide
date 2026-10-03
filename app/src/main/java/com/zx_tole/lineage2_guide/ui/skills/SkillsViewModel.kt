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
    val allSkills: List<Skill> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
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
                                allSkills = skills,
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
                        val query = _uiState.value.searchQuery
                        val filtered = if (query.isNotBlank()) {
                            skills.filter { s ->
                                s.name.contains(query, ignoreCase = true) ||
                                    s.classRestriction.contains(query, ignoreCase = true) ||
                                    s.type.name.contains(query, ignoreCase = true) ||
                                    s.description?.contains(query, ignoreCase = true) == true
                            }
                        } else {
                            skills
                        }
                        _uiState.update {
                            it.copy(
                                allSkills = skills,
                                skills = filtered,
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
                state.allSkills.filter { s ->
                    s.name.contains(query, ignoreCase = true) ||
                        s.classRestriction.contains(query, ignoreCase = true) ||
                        s.type.name.contains(query, ignoreCase = true) ||
                        s.description?.contains(query, ignoreCase = true) == true
                }
            } else {
                state.allSkills
            }
            state.copy(
                skills = filtered,
                searchQuery = query,
                isLoading = false,
                error = null
            )
        }
    }
}
