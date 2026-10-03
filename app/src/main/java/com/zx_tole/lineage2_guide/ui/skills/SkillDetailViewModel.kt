package com.zx_tole.lineage2_guide.ui.skills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zx_tole.lineage2_guide.domain.model.Skill
import com.zx_tole.lineage2_guide.domain.usecase.GetSkillsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

data class SkillDetailUiState(
    val skill: Skill? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class SkillDetailViewModel(
    private val getSkillsUseCase: GetSkillsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SkillDetailUiState(isLoading = true))
    val uiState: StateFlow<SkillDetailUiState> = _uiState.asStateFlow()

    fun loadSkill(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                getSkillsUseCase.invoke()
                    .catch { e ->
                        Timber.e(e, "Error loading skill")
                        _uiState.update { it.copy(isLoading = false, error = e.message) }
                    }
                    .collect { skills ->
                        val found = skills.find { s -> s.id == id }
                        if (found != null) {
                            _uiState.update {
                                it.copy(skill = found, isLoading = false, error = null)
                            }
                        } else {
                            _uiState.update {
                                it.copy(isLoading = false, error = "Skill not found")
                            }
                        }
                    }
            } catch (e: Exception) {
                Timber.e(e, "Error in skill detail")
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
