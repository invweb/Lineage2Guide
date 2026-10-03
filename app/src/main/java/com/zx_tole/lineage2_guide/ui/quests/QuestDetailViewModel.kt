package com.zx_tole.lineage2_guide.ui.quests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zx_tole.lineage2_guide.domain.model.Quest
import com.zx_tole.lineage2_guide.domain.usecase.GetQuestsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

data class QuestDetailUiState(
    val quest: Quest? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class QuestDetailViewModel(
    private val getQuestsUseCase: GetQuestsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuestDetailUiState(isLoading = true))
    val uiState: StateFlow<QuestDetailUiState> = _uiState.asStateFlow()

    fun loadQuest(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                getQuestsUseCase.invoke()
                    .catch { e ->
                        Timber.e(e, "Error loading quest")
                        _uiState.update { it.copy(isLoading = false, error = e.message) }
                    }
                    .collect { quests ->
                        val found = quests.find { q -> q.id == id }
                        if (found != null) {
                            _uiState.update {
                                it.copy(quest = found, isLoading = false, error = null)
                            }
                        } else {
                            _uiState.update {
                                it.copy(isLoading = false, error = "Quest not found")
                            }
                        }
                    }
            } catch (e: Exception) {
                Timber.e(e, "Error in quest detail")
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
