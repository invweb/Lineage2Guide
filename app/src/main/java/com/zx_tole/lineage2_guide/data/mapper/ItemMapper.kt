package com.zx_tole.lineage2_guide.data.mapper

import com.zx_tole.lineage2_guide.data.local.entity.ItemEntity
import com.zx_tole.lineage2_guide.data.network.dto.ItemDto
import com.zx_tole.lineage2_guide.domain.model.Item
import com.zx_tole.lineage2_guide.domain.model.ItemType
import com.zx_tole.lineage2_guide.domain.model.Rarity

class ItemMapper {

    fun toDomain(dto: ItemDto): Item = Item(
        id = dto.id,
        name = dto.name,
        classRestriction = dto.classRestriction,
        level = dto.level,
        type = ItemType.fromString(dto.type),
        rarity = Rarity.fromString(dto.rarity),
        location = dto.location,
        dropInfo = dto.dropInfo,
        stats = dto.stats,
        description = dto.description,
        iconUrl = dto.iconUrl
    )

    fun toDomain(entity: ItemEntity): Item = Item(
        id = entity.id,
        name = entity.name,
        classRestriction = entity.classRestriction,
        level = entity.level,
        type = ItemType.fromString(entity.type),
        rarity = Rarity.fromString(entity.rarity),
        location = entity.location,
        dropInfo = entity.dropInfo,
        stats = parseStats(entity.stats),
        description = entity.description,
        iconUrl = entity.iconUrl
    )

    fun toEntity(dto: ItemDto): ItemEntity = ItemEntity(
        id = dto.id,
        name = dto.name,
        classRestriction = dto.classRestriction,
        level = dto.level,
        type = dto.type,
        rarity = dto.rarity,
        location = dto.location,
        dropInfo = dto.dropInfo,
        stats = dto.stats.entries.joinToString("|") { it.key + ":" + it.value },
        description = dto.description,
        iconUrl = dto.iconUrl
    )

    fun toEntity(domain: Item): ItemEntity = ItemEntity(
        id = domain.id,
        name = domain.name,
        classRestriction = domain.classRestriction,
        level = domain.level,
        type = domain.type.name,
        rarity = domain.rarity.name,
        location = domain.location,
        dropInfo = domain.dropInfo,
        stats = domain.stats.entries.joinToString("|") { it.key + ":" + it.value },
        description = domain.description,
        iconUrl = domain.iconUrl
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
