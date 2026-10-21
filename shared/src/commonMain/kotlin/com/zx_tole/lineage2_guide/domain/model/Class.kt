package com.zx_tole.lineage2_guide.domain.model

data class GameClass(
    val id: Long,
    val name: String,
    val race: String,
    val subClasses: List<String>,
    val description: String?,
    val iconUrl: String?,
    val baseStats: Map<String, Int>
)
