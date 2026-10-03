package com.zx_tole.lineage2_guide.data.repository

import com.zx_tole.lineage2_guide.data.local.dao.QuestsDao
import com.zx_tole.lineage2_guide.data.mapper.QuestMapper
import com.zx_tole.lineage2_guide.data.network.Lineage2ApiService
import com.zx_tole.lineage2_guide.domain.model.Quest
import com.zx_tole.lineage2_guide.domain.repository.QuestsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class QuestsRepositoryImpl(
    private val questsDao: QuestsDao,
    private val apiService: Lineage2ApiService,
    private val questMapper: QuestMapper
) : QuestsRepository {

    override fun getQuests(type: String?, startLevel: Int?, npcId: Long?): Flow<List<Quest>> {
        return questsDao.getQuestsByFilter(type, startLevel, npcId, 100, 0)
            .map { entities -> entities.map { questMapper.toDomain(it) } }
            .catch { e ->
                Timber.e(e, "Error fetching quests from cache")
                emit(emptyList())
            }
            .flowOn(Dispatchers.IO)
    }

    override fun getQuestById(id: Long): Flow<Quest?> {
        return questsDao.getQuestById(id)
            .map { entity -> entity?.let { questMapper.toDomain(it) } }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun refreshQuests() = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getQuests()
            val entities = response.quests.map { questMapper.toEntity(it) }
            questsDao.upsertQuests(entities)
            Timber.d("Quests refreshed: ${entities.size} quests")
        } catch (e: Exception) {
            Timber.e(e, "Failed to refresh quests")
        }
    }

    override suspend fun clearCache() = withContext(Dispatchers.IO) {
        questsDao.clearQuests()
    }
}
