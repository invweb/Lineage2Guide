package com.zx_tole.lineage2_guide.data.repository

import com.zx_tole.lineage2_guide.data.local.dao.NpcsDao
import com.zx_tole.lineage2_guide.data.mapper.NpcMapper
import com.zx_tole.lineage2_guide.data.network.Lineage2ApiService
import com.zx_tole.lineage2_guide.domain.model.Npc
import com.zx_tole.lineage2_guide.domain.repository.NpcsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class NpcsRepositoryImpl(
    private val npcsDao: NpcsDao,
    private val apiService: Lineage2ApiService,
    private val npcMapper: NpcMapper
) : NpcsRepository {

    override fun getNpcs(type: String?, location: String?, searchQuery: String?): Flow<List<Npc>> {
        return npcsDao.getNpcsByFilter(type, location, searchQuery)
            .map { entities -> entities.map { npcMapper.toDomain(it) } }
            .catch { e ->
                Timber.e(e, "Error fetching npcs from cache")
                emit(emptyList())
            }
            .flowOn(Dispatchers.IO)
    }

    override fun getNpcById(id: Long): Flow<Npc?> {
        return npcsDao.getNpcById(id)
            .map { entity -> entity?.let { npcMapper.toDomain(it) } }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun refreshNpcs() = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getNpcs()
            val entities = response.npcs.map { npcMapper.toEntity(it) }
            npcsDao.upsertNpcs(entities)
            Timber.d("NPCs refreshed: ${entities.size} NPCs")
        } catch (e: Exception) {
            Timber.e(e, "Failed to refresh NPCs")
        }
    }

    override suspend fun clearCache() = withContext(Dispatchers.IO) {
        npcsDao.clearNpcs()
    }
}
