package com.zx_tole.lineage2_guide.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "skills",
    indices = [
        Index(value = ["classRestriction"]),
        Index(value = ["level"]),
        Index(value = ["type"]),
        Index(value = ["classRestriction", "type"]),
        Index(value = ["classRestriction", "level"])
    ]
)
data class SkillEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val classRestriction: String,
    val level: Int,
    val type: String,
    val description: String?,
    val cooldown: Int?,
    val iconUrl: String?,
    val manaCost: Int?,
    val range: String?,
    val updatedAt: Long = System.currentTimeMillis()
)
