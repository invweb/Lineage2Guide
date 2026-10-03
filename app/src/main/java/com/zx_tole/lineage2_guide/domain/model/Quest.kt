package com.zx_tole.lineage2_guide.domain.model

data class Quest(
    val id: Long,
    val name: String,
    val npcId: Long?,
    val npcName: String? = null,
    val startLevel: Int,
    val reward: List<Reward>,
    val description: String?,
    val type: QuestType,
    val prerequisites: List<Long>,
    val progressStatus: QuestProgress?
)

data class Reward(
    val type: String,
    val value: Int
)

enum class QuestType {
    MAIN, SIDE, ARENA;

    companion object {
        fun fromString(value: String): QuestType = values().find { it.name.equals(value, ignoreCase = true) } ?: SIDE
    }
}

enum class QuestProgress {
    NOT_STARTED, IN_PROGRESS, COMPLETED;

    companion object {
        fun fromString(value: String): QuestProgress? = values().find { it.name.equals(value, ignoreCase = true) }
    }
}
