package com.zx_tole.lineage2_guide.domain.model

data class Npc(
    val id: Long,
    val name: String,
    val type: NpcType,
    val location: String,
    val description: String?,
    val relatedQuestIds: List<Long>,
    val iconUrl: String?
)

enum class NpcType {
    QUEST_GIVER, MERCHANT, BOSS, MONSTER, TEACHER, OTHER;

    companion object {
        fun fromString(value: String): NpcType = values().find { it.name.equals(value, ignoreCase = true) } ?: OTHER
    }
}
