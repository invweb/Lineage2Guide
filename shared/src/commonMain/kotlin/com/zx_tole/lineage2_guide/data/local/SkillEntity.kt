package com.zx_tole.lineage2_guide.data.local

data class SkillEntity(
    val id: Long,
    val name: String,
    val classRestriction: String,
    val level: Int,
    val type: String,
    val description: String?,
    val cooldown: Int?,
    val iconUrl: String?,
    val manaCost: Int?,
    val range: String?,
    val updatedAt: Long
)
