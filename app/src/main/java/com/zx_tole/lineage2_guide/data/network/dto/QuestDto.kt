package com.zx_tole.lineage2_guide.data.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuestsResponse(
    val quests: List<QuestDto>
)

@JsonClass(generateAdapter = true)
data class QuestResponse(
    val quest: QuestDto
)

@JsonClass(generateAdapter = true)
data class QuestDto(
    val id: Long,
    val name: String,
    val npcId: Long?,
    val startLevel: Int,
    val reward: List<RewardDto>,
    val description: String?,
    val type: String,
    val prerequisites: List<Long> = emptyList(),
    val progressStatus: String?
)

@JsonClass(generateAdapter = true)
data class RewardDto(
    val type: String,
    val value: Int
)
