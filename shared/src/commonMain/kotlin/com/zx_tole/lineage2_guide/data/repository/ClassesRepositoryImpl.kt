package com.zx_tole.lineage2_guide.data.repository

import com.zx_tole.lineage2_guide.data.mapper.ClassMapper
import com.zx_tole.lineage2_guide.data.network.Lineage2ApiService
import com.zx_tole.lineage2_guide.domain.model.GameClass
import com.zx_tole.lineage2_guide.domain.repository.ClassesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

class ClassesRepositoryImpl(
    private val apiService: Lineage2ApiService,
    private val classMapper: ClassMapper
) : ClassesRepository {
    private val _classes = MutableStateFlow<List<GameClass>>(emptyList())
    override fun getClasses(race: String?, searchQuery: String?): Flow<List<GameClass>> = _classes.asStateFlow()
    override fun getClassById(id: Long): Flow<GameClass?> = flow { emit(_classes.value.find { it.id == id }) }
    override suspend fun refreshClasses() { _classes.value = apiService.getClasses().classes.map { classMapper.toDomain(it) } }
    override suspend fun clearCache() { _classes.value = emptyList() }
}
