package com.zx_tole.lineage2_guide.domain.model

data class Skill(
    val id: Long,
    val name: String,
    val classRestriction: String,
    val level: Int,
    val type: SkillType,
    val description: String?,
    val cooldown: Int?,
    val iconUrl: String?,
    val manaCost: Int?,
    val range: String?
)

enum class SkillType {
    ACTIVE, PASSIVE;

    companion object {
        fun fromString(value: String): SkillType = values().find { it.name.equals(value, ignoreCase = true) } ?: PASSIVE
    }
}
