package com.zx_tole.lineage2_guide.domain.model

data class ItemsFilter(
    val classRestriction: String? = null,
    val minLevel: Int? = null,
    val maxLevel: Int? = null,
    val type: String? = null,
    val rarity: String? = null,
    val location: String? = null,
    val searchQuery: String? = null,
    val sortBy: SortOption = SortOption.NAME_ASC,
    val page: Int = 0,
    val pageSize: Int = 20
)
