package com.zx_tole.lineage2_guide.domain.model

data class Item(
    val id: Long,
    val name: String,
    val classRestriction: String?,
    val level: Int,
    val type: ItemType,
    val rarity: Rarity,
    val location: String?,
    val dropInfo: String?,
    val stats: Map<String, Int>,
    val description: String?,
    val iconUrl: String?
)

enum class ItemType {
    WEAPON, ARMOR, BOOTS, GLOVES, HELMET, SHIELD, POTION, SCROLL, MATERIAL, JEWELRY, OTHER;

    companion object {
        fun fromString(value: String): ItemType = values().find { it.name.equals(value, ignoreCase = true) } ?: OTHER
    }
}

enum class Rarity {
    COMMON, UNCOMMON, RARE, EPIC, LEGENDARY, DIVINE;

    companion object {
        fun fromString(value: String): Rarity = values().find { it.name.equals(value, ignoreCase = true) } ?: COMMON
    }

    fun getOrdinalInt(): Int = ordinal
}
