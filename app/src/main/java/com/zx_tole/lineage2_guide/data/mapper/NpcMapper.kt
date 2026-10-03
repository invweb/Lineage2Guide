package com.zx_tole.lineage2_guide.data.mapper

import com.zx_tole.lineage2_guide.data.local.entity.NpcEntity
import com.zx_tole.lineage2_guide.data.network.dto.NpcDto
import com.zx_tole.lineage2_guide.domain.model.Npc

class NpcMapper {

    fun toDomain(dto: NpcDto): Npc = Npc(
        id = dto.id,
        name = dto.name,
        type = com.zx_tole.lineage2_guide.domain.model.NpcType.fromString(dto.type),
        location = dto.location,
        description = dto.description,
        relatedQuestIds = dto.relatedQuestIds,
        iconUrl = dto.iconUrl
    )

    fun toDomain(entity: NpcEntity): Npc = Npc(
        id = entity.id,
        name = entity.name,
        type = com.zx_tole.lineage2_guide.domain.model.NpcType.fromString(entity.type),
        location = entity.location,
        description = entity.description,
        relatedQuestIds = entity.relatedQuestIds.split(",").filter { it.isNotEmpty() }.map { it.toLongOrNull() ?: 0L },
        iconUrl = entity.iconUrl
    )

    fun toEntity(dto: NpcDto): NpcEntity = NpcEntity(
        id = dto.id,
        name = dto.name,
        type = dto.type,
        location = dto.location,
        description = dto.description,
        relatedQuestIds = dto.relatedQuestIds.joinToString(","),
        iconUrl = dto.iconUrl,
        updatedAt = System.currentTimeMillis()
    )
}
