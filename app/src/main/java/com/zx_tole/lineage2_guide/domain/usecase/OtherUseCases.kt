package com.zx_tole.lineage2_guide.domain.usecase

import com.zx_tole.lineage2_guide.domain.model.GameClass
import com.zx_tole.lineage2_guide.domain.model.Quest
import com.zx_tole.lineage2_guide.domain.model.Npc
import com.zx_tole.lineage2_guide.domain.model.Skill
import com.zx_tole.lineage2_guide.domain.repository.ClassesRepository
import com.zx_tole.lineage2_guide.domain.repository.QuestsRepository
import com.zx_tole.lineage2_guide.domain.repository.NpcsRepository
import com.zx_tole.lineage2_guide.domain.repository.SkillsRepository
import kotlinx.coroutines.flow.Flow

class GetQuestsUseCase(
    private val repository: QuestsRepository
) {
    operator fun invoke(type: String? = null, startLevel: Int? = null, npcId: Long? = null): Flow<List<Quest>> {
        return repository.getQuests(type, startLevel, npcId)
    }
}

class GetSkillsUseCase(
    private val repository: SkillsRepository
) {
    operator fun invoke(classRestriction: String? = null, type: String? = null, minLevel: Int? = null, maxLevel: Int? = null): Flow<List<Skill>> {
        return repository.getSkills(classRestriction, type, minLevel, maxLevel)
    }
}

class GetClassesUseCase(
    private val repository: ClassesRepository
) {
    operator fun invoke(race: String? = null, searchQuery: String? = null): Flow<List<GameClass>> {
        return repository.getClasses(race, searchQuery)
    }
}

class GetNpcsUseCase(
    private val repository: NpcsRepository
) {
    operator fun invoke(type: String? = null, location: String? = null, searchQuery: String? = null): Flow<List<Npc>> {
        return repository.getNpcs(type, location, searchQuery)
    }
}
