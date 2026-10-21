package com.zx_tole.lineage2_guide.data.local

data class ItemEntity(
    val id: Long,
    val name: String,
    val classRestriction: String?,
    val level: Int,
    val type: String,
    val rarity: String,
    val location: String?,
    val dropInfo: String?,
    val stats: String,
    val description: String?,
    val iconUrl: String?,
    val updatedAt: Long
)
