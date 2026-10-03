package com.zx_tole.lineage2_guide.domain.model

sealed interface ItemsUiState {
    data class Success(
        val items: List<Item> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
        val filterState: FilterState = FilterState(),
        val isRefreshing: Boolean = false
    ) : ItemsUiState {
        companion object {
            fun empty() = Success(isLoading = true)
        }
    }

    data class Loading(val filterState: FilterState = FilterState()) : ItemsUiState
    data class Error(val message: String, val filterState: FilterState = FilterState()) : ItemsUiState
}

data class FilterState(
    val selectedClasses: Set<String> = emptySet(),
    val levelRange: IntRange? = null,
    val selectedTypes: Set<String> = emptySet(),
    val selectedRarities: Set<String> = emptySet(),
    val selectedLocations: Set<String> = emptySet(),
    val searchQuery: String = "",
    val sortBy: SortOption = SortOption.NAME_ASC
)

enum class SortOption {
    NAME_ASC, NAME_DESC, LEVEL_ASC, LEVEL_DESC, RARITY
}
