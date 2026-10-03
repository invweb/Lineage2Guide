package com.zx_tole.lineage2_guide.data.mapper

import com.zx_tole.lineage2_guide.data.local.entity.SkillEntity
import com.zx_tole.lineage2_guide.data.network.dto.SkillDto
import com.zx_tole.lineage2_guide.domain.model.Skill
import com.zx_tole.lineage2_guide.domain.model.SkillType

class SkillMapper {

    fun toDomain(dto: SkillDto): Skill = Skill(
        id = dto.id,
        name = dto.name,
        classRestriction = dto.classRestriction,
        level = dto.level,
        type = SkillType.fromString(dto.type),
        description = dto.description,
        cooldown = dto.cooldown,
        iconUrl = dto.iconUrl,
        manaCost = dto.manaCost,
        range = dto.range
    )

    fun toDomain(entity: SkillEntity): Skill = Skill(
        id = entity.id,
        name = entity.name,
        classRestriction = entity.classRestriction,
        level = entity.level,
        type = SkillType.fromString(entity.type),
        description = entity.description,
        cooldown = entity.cooldown,
        iconUrl = entity.iconUrl,
        manaCost = entity.manaCost,
        range = entity.range
    )

    fun toEntity(dto: SkillDto): SkillEntity = SkillEntity(
        id = dto.id,
        name = dto.name,
        classRestriction = dto.classRestriction,
        level = dto.level,
        type = dto.type,
        description = dto.description,
        cooldown = dto.cooldown,
        iconUrl = dto.iconUrl,
        manaCost = dto.manaCost,
        range = dto.range,
        updatedAt = System.currentTimeMillis()
    )
}
