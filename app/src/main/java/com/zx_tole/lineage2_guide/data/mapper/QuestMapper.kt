package com.zx_tole.lineage2_guide.data.mapper

import com.zx_tole.lineage2_guide.data.local.entity.QuestEntity
import com.zx_tole.lineage2_guide.data.network.dto.QuestDto
import com.zx_tole.lineage2_guide.domain.model.*

class QuestMapper {

    fun toDomain(dto: QuestDto): Quest = Quest(
        id = dto.id,
        name = dto.name,
        npcId = dto.npcId,
        startLevel = dto.startLevel,
        reward = dto.reward.map { Reward(it.type, it.value) },
        description = dto.description,
        type = QuestType.fromString(dto.type),
        prerequisites = dto.prerequisites,
        progressStatus = dto.progressStatus?.let { QuestProgress.fromString(it) }
    )

    fun toDomain(entity: QuestEntity): Quest = Quest(
        id = entity.id,
        name = entity.name,
        npcId = entity.npcId,
        startLevel = entity.startLevel,
        reward = entity.reward.split("|").mapNotNull { part ->
            val parts = part.split(":")
            if (parts.size == 2) Reward(parts[0], parts[1].toIntOrNull() ?: 0) else null
        },
        description = entity.description,
        type = QuestType.fromString(entity.type),
        prerequisites = entity.prerequisites?.split(",")?.mapNotNull { it.toLongOrNull() } ?: emptyList(),
        progressStatus = entity.progressStatus?.let { QuestProgress.fromString(it) }
    )

    fun toEntity(dto: QuestDto): QuestEntity = QuestEntity(
        id = dto.id,
        name = dto.name,
        npcId = dto.npcId,
        startLevel = dto.startLevel,
        reward = dto.reward.joinToString("|") { it.type + ":" + it.value },
        description = dto.description,
        type = dto.type,
        prerequisites = dto.prerequisites?.joinToString(",") ?: "",
        progressStatus = dto.progressStatus,
        updatedAt = System.currentTimeMillis()
    )
}
