package com.zx_tole.lineage2_guide.data.repository

import com.zx_tole.lineage2_guide.data.local.dao.SkillsDao
import com.zx_tole.lineage2_guide.data.mapper.SkillMapper
import com.zx_tole.lineage2_guide.data.network.Lineage2ApiService
import com.zx_tole.lineage2_guide.domain.model.Skill
import com.zx_tole.lineage2_guide.domain.repository.SkillsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class SkillsRepositoryImpl(
    private val skillsDao: SkillsDao,
    private val apiService: Lineage2ApiService,
    private val skillMapper: SkillMapper
) : SkillsRepository {

    override fun getSkills(classRestriction: String?, type: String?, minLevel: Int?, maxLevel: Int?): Flow<List<Skill>> {
        return skillsDao.getSkillsByFilter(classRestriction, type, minLevel, maxLevel, null, 100, 0)
            .map { entities -> entities.map { skillMapper.toDomain(it) } }
            .catch { e ->
                Timber.e(e, "Error fetching skills from cache")
                emit(emptyList())
            }
            .flowOn(Dispatchers.IO)
    }

    override fun getSkillById(id: Long): Flow<Skill?> {
        return skillsDao.getSkillById(id)
            .map { entity -> entity?.let { skillMapper.toDomain(it) } }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun refreshSkills() = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getSkills()
            val entities = response.skills.map { skillMapper.toEntity(it) }
            skillsDao.upsertSkills(entities)
            Timber.d("Skills refreshed: ${entities.size} skills")
        } catch (e: Exception) {
            Timber.e(e, "Failed to refresh skills")
        }
    }

    override suspend fun clearCache() = withContext(Dispatchers.IO) {
        skillsDao.clearSkills()
    }
}
