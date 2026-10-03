package com.zx_tole.lineage2_guide.data.mapper

import com.zx_tole.lineage2_guide.data.local.entity.ClassEntity
import com.zx_tole.lineage2_guide.data.network.dto.ClassDto
import com.zx_tole.lineage2_guide.domain.model.GameClass

class ClassMapper {

    fun toDomain(dto: ClassDto): GameClass = GameClass(
        id = dto.id,
        name = dto.name,
        race = dto.race,
        subClasses = dto.subClasses,
        description = dto.description,
        iconUrl = dto.iconUrl,
        baseStats = dto.baseStats
    )

    fun toDomain(entity: ClassEntity): GameClass = GameClass(
        id = entity.id,
        name = entity.name,
        race = entity.race,
        subClasses = entity.subClasses.split("|").filter { it.isNotEmpty() },
        description = entity.description,
        iconUrl = entity.iconUrl,
        baseStats = parseStats(entity.baseStats)
    )

    fun toEntity(dto: ClassDto): ClassEntity = ClassEntity(
        id = dto.id,
        name = dto.name,
        race = dto.race,
        subClasses = dto.subClasses.joinToString("|"),
        description = dto.description,
        iconUrl = dto.iconUrl,
        baseStats = dto.baseStats.entries.joinToString("|") { it.key + ":" + it.value },
        updatedAt = System.currentTimeMillis()
    )

    private fun parseStats(stats: String): Map<String, Int> {
        if (stats.isBlank()) return emptyMap()
        val result = mutableMapOf<String, Int>()
        for (pair in stats.split("|")) {
            val parts = pair.split(":")
            if (parts.size == 2) {
                result[parts[0]] = parts[1].toIntOrNull() ?: 0
            }
        }
        return result
    }
}
