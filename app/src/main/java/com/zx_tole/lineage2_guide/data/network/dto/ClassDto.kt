package com.zx_tole.lineage2_guide.data.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ClassesResponse(
    val classes: List<ClassDto>
)

@JsonClass(generateAdapter = true)
data class ClassDto(
    val id: Long,
    val name: String,
    val race: String,
    val subClasses: List<String>,
    val description: String?,
    val iconUrl: String?,
    val baseStats: Map<String, Int> = emptyMap()
)
