package com.zx_tole.lineage2_guide.ui.skills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zx_tole.lineage2_guide.domain.model.Skill
import com.zx_tole.lineage2_guide.domain.repository.SkillsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class SkillDetailUiState(
    val skill: Skill? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class SkillDetailViewModel(
    private val skillsRepository: SkillsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SkillDetailUiState(isLoading = true))
    val uiState: StateFlow<SkillDetailUiState> = _uiState.asStateFlow()

    fun loadSkill(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val skill = skillsRepository.getSkillById(id).first()
                if (skill != null) {
                    _uiState.update { it.copy(skill = skill, isLoading = false, error = null) }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = "Skill not found") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
