package com.zx_tole.lineage2_guide.data.local

data class QuestEntity(
    val id: Long,
    val name: String,
    val npcId: Long?,
    val startLevel: Int,
    val reward: String,
    val description: String?,
    val type: String,
    val prerequisites: String,
    val progressStatus: String?,
    val updatedAt: Long
)
