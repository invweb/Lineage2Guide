package com.zx_tole.lineage2_guide.data.repository

import com.zx_tole.lineage2_guide.data.mapper.QuestMapper
import com.zx_tole.lineage2_guide.data.network.Lineage2ApiService
import com.zx_tole.lineage2_guide.domain.model.Quest
import com.zx_tole.lineage2_guide.domain.repository.QuestsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

class QuestsRepositoryImpl(
    private val apiService: Lineage2ApiService,
    private val questMapper: QuestMapper
) : QuestsRepository {
    private val _quests = MutableStateFlow<List<Quest>>(emptyList())
    override fun getQuests(type: String?, startLevel: Int?, npcId: Long?): Flow<List<Quest>> = _quests.asStateFlow()
    override fun getQuestById(id: Long): Flow<Quest?> = flow { emit(_quests.value.find { it.id == id }) }
    override suspend fun refreshQuests() { _quests.value = apiService.getQuests().quests.map { questMapper.toDomain(it) } }
    override suspend fun clearCache() { _quests.value = emptyList() }
}
