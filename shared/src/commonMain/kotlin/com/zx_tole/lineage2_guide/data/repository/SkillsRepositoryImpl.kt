package com.zx_tole.lineage2_guide.data.repository

import com.zx_tole.lineage2_guide.data.mapper.SkillMapper
import com.zx_tole.lineage2_guide.data.network.Lineage2ApiService
import com.zx_tole.lineage2_guide.domain.model.Skill
import com.zx_tole.lineage2_guide.domain.repository.SkillsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

class SkillsRepositoryImpl(
    private val apiService: Lineage2ApiService,
    private val skillMapper: SkillMapper
) : SkillsRepository {
    private val _skills = MutableStateFlow<List<Skill>>(emptyList())
    override fun getSkills(classRestriction: String?, type: String?, minLevel: Int?, maxLevel: Int?): Flow<List<Skill>> = _skills.asStateFlow()
    override fun getSkillById(id: Long): Flow<Skill?> = flow { emit(_skills.value.find { it.id == id }) }
    override suspend fun refreshSkills() { _skills.value = apiService.getSkills().skills.map { skillMapper.toDomain(it) } }
    override suspend fun clearCache() { _skills.value = emptyList() }
}
