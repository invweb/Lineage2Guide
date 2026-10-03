package com.zx_tole.lineage2_guide.ui.npcs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zx_tole.lineage2_guide.domain.model.Npc
import com.zx_tole.lineage2_guide.domain.usecase.GetNpcsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

data class NpcDetailUiState(
    val npc: Npc? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class NpcDetailViewModel(
    private val getNpcsUseCase: GetNpcsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NpcDetailUiState(isLoading = true))
    val uiState: StateFlow<NpcDetailUiState> = _uiState.asStateFlow()

    fun loadNpc(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                getNpcsUseCase.invoke()
                    .catch { e ->
                        Timber.e(e, "Error loading npc")
                        _uiState.update { it.copy(isLoading = false, error = e.message) }
                    }
                    .collect { npcs ->
                        val found = npcs.find { n -> n.id == id }
                        if (found != null) {
                            _uiState.update {
                                it.copy(npc = found, isLoading = false, error = null)
                            }
                        } else {
                            _uiState.update {
                                it.copy(isLoading = false, error = "NPC not found")
                            }
                        }
                    }
            } catch (e: Exception) {
                Timber.e(e, "Error in npc detail")
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
