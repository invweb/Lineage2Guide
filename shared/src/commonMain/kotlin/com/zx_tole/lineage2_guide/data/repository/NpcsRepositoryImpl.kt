package com.zx_tole.lineage2_guide.data.repository

import com.zx_tole.lineage2_guide.data.mapper.NpcMapper
import com.zx_tole.lineage2_guide.data.network.Lineage2ApiService
import com.zx_tole.lineage2_guide.domain.model.Npc
import com.zx_tole.lineage2_guide.domain.repository.NpcsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

class NpcsRepositoryImpl(
    private val apiService: Lineage2ApiService,
    private val npcMapper: NpcMapper
) : NpcsRepository {
    private val _npcs = MutableStateFlow<List<Npc>>(emptyList())
    override fun getNpcs(type: String?, location: String?, searchQuery: String?): Flow<List<Npc>> = _npcs.asStateFlow()
    override fun getNpcById(id: Long): Flow<Npc?> = flow { emit(_npcs.value.find { it.id == id }) }
    override suspend fun refreshNpcs() { _npcs.value = apiService.getNpcs().npcs.map { npcMapper.toDomain(it) } }
    override suspend fun clearCache() { _npcs.value = emptyList() }
}
