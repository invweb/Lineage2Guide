package com.zx_tole.lineage2_guide.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "items",
    indices = [
        Index(value = ["classRestriction"]),
        Index(value = ["level"]),
        Index(value = ["type"]),
        Index(value = ["rarity"]),
        Index(value = ["location"]),
        Index(value = ["classRestriction", "level"]),
        Index(value = ["classRestriction", "rarity"])
    ]
)
data class ItemEntity(
    @PrimaryKey val id: Long,
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
    val updatedAt: Long = System.currentTimeMillis()
)
