package com.zx_tole.lineage2_guide.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "quests",
    indices = [
        Index(value = ["type"]),
        Index(value = ["startLevel"]),
        Index(value = ["npcId"]),
        Index(value = ["type", "startLevel"])
    ]
)
data class QuestEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val npcId: Long?,
    val startLevel: Int,
    val reward: String,
    val description: String?,
    val type: String,
    val prerequisites: String?,
    val progressStatus: String?,
    val updatedAt: Long = System.currentTimeMillis()
)
