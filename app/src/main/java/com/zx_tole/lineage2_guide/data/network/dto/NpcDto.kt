package com.zx_tole.lineage2_guide.data.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NpcsResponse(
    val npcs: List<NpcDto>
)

@JsonClass(generateAdapter = true)
data class NpcDto(
    val id: Long,
    val name: String,
    val type: String,
    val location: String,
    val description: String?,
    val relatedQuestIds: List<Long> = emptyList(),
    val iconUrl: String?
)

@JsonClass(generateAdapter = true)
data class SyncResponse(
    val lastSync: Long,
    val hasUpdates: Boolean,
    val updatedEntities: Map<String, Long> = emptyMap()
)
