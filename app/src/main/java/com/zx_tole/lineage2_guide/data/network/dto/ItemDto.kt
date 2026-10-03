package com.zx_tole.lineage2_guide.data.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ItemsResponse(
    val items: List<ItemDto>
)

@JsonClass(generateAdapter = true)
data class ItemResponse(
    val item: ItemDto
)

@JsonClass(generateAdapter = true)
data class ItemDto(
    val id: Long,
    val name: String,
    val classRestriction: String?,
    val level: Int,
    val type: String,
    val rarity: String,
    val location: String?,
    val dropInfo: String?,
    val stats: Map<String, Int> = emptyMap(),
    val description: String?,
    val iconUrl: String?
)
