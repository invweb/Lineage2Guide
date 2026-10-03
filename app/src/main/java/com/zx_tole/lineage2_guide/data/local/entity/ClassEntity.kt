package com.zx_tole.lineage2_guide.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "classes",
    indices = [
        Index(value = ["name"], unique = true),
        Index(value = ["race"])
    ]
)
data class ClassEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val race: String,
    val subClasses: String,
    val description: String?,
    val iconUrl: String?,
    val baseStats: String,
    val updatedAt: Long = System.currentTimeMillis()
)
