package com.zx_tole.lineage2_guide.data.local

data class NpcEntity(
    val id: Long,
    val name: String,
    val type: String,
    val location: String,
    val description: String?,
    val relatedQuestIds: String,
    val iconUrl: String?,
    val updatedAt: Long
)
