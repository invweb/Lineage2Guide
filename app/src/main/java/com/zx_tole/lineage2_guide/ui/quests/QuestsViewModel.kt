package com.zx_tole.lineage2_guide.ui.quests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zx_tole.lineage2_guide.domain.model.Quest
import com.zx_tole.lineage2_guide.domain.usecase.GetQuestsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

data class QuestsUiState(
    val quests: List<Quest> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val filterType: String? = null
)

class QuestsViewModel(
    private val getQuestsUseCase: GetQuestsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuestsUiState(isLoading = true))
    val uiState: StateFlow<QuestsUiState> = _uiState.asStateFlow()

    init {
        loadQuests()
    }

    private fun loadQuests() {
        viewModelScope.launch {
            try {
                getQuestsUseCase.invoke()
                    .catch { e ->
                        Timber.e(e, "Error loading quests")
                        _uiState.update { it.copy(isLoading = false, error = e.message) }
                    }
                    .collect { quests ->
                        _uiState.update {
                            it.copy(
                                quests = quests,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
            } catch (e: Exception) {
                Timber.e(e, "Error in quests flow")
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            try {
                getQuestsUseCase.invoke()
                    .catch { e ->
                        Timber.e(e, "Error refreshing quests")
                    }
                    .collect { quests ->
                        _uiState.update {
                            it.copy(
                                quests = quests,
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

    fun filterByType(type: String?) {
        viewModelScope.launch {
            _uiState.update { it.copy(filterType = type, isLoading = true) }
            try {
                getQuestsUseCase.invoke(type = type)
                    .catch { e ->
                        Timber.e(e, "Error filtering quests")
                    }
                    .collect { quests ->
                        _uiState.update {
                            it.copy(
                                quests = quests,
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
