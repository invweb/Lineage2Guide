package com.zx_tole.lineage2_guide.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "npcs",
    indices = [
        Index(value = ["name"]),
        Index(value = ["type"]),
        Index(value = ["location"])
    ]
)
data class NpcEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val type: String,
    val location: String,
    val description: String?,
    val relatedQuestIds: String,
    val iconUrl: String?,
    val updatedAt: Long = System.currentTimeMillis()
)
