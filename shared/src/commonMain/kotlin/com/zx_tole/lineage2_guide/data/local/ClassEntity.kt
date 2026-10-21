package com.zx_tole.lineage2_guide.data.local

data class ClassEntity(
    val id: Long,
    val name: String,
    val race: String,
    val subClasses: String,
    val description: String?,
    val iconUrl: String?,
    val baseStats: String,
    val updatedAt: Long
)
