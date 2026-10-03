package com.zx_tole.lineage2_guide.data.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ClassesResponse(
    val classes: List<ClassDto>
)

@JsonClass(generateAdapter = true)
data class ClassResponse(
    val classData: ClassDto
)

@JsonClass(generateAdapter = true)
data class ClassDto(
    val id: Long,
    val name: String,
    val race: String,
    val subClasses: List<String>,
    val description: String?,
    val iconUrl: String?,
    @Json(name = "baseStats") val baseStatsList: List<MapEntryDto> = emptyList()
) {
    val baseStats: Map<String, Int>
        get() = baseStatsList.associate { it.key to it.value }
}

@JsonClass(generateAdapter = true)
data class MapEntryDto(
    val key: String,
    val value: Int
)
