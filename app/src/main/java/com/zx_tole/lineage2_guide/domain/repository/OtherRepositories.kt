package com.zx_tole.lineage2_guide.domain.repository

import com.zx_tole.lineage2_guide.domain.model.GameClass
import com.zx_tole.lineage2_guide.domain.model.Quest
import com.zx_tole.lineage2_guide.domain.model.Npc
import com.zx_tole.lineage2_guide.domain.model.Skill
import kotlinx.coroutines.flow.Flow

interface QuestsRepository {
    fun getQuests(type: String?, startLevel: Int?, npcId: Long?): Flow<List<Quest>>
    fun getQuestById(id: Long): Flow<Quest?>
    suspend fun refreshQuests()
    suspend fun clearCache()
}

interface SkillsRepository {
    fun getSkills(classRestriction: String?, type: String?, minLevel: Int?, maxLevel: Int?): Flow<List<Skill>>
    fun getSkillById(id: Long): Flow<Skill?>
    suspend fun refreshSkills()
    suspend fun clearCache()
}

interface ClassesRepository {
    fun getClasses(race: String?, searchQuery: String?): Flow<List<GameClass>>
    fun getClassById(id: Long): Flow<GameClass?>
    suspend fun refreshClasses()
    suspend fun clearCache()
}

interface NpcsRepository {
    fun getNpcs(type: String?, location: String?, searchQuery: String?): Flow<List<Npc>>
    fun getNpcById(id: Long): Flow<Npc?>
    suspend fun refreshNpcs()
    suspend fun clearCache()
}
