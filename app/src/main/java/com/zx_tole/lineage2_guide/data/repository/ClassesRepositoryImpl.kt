package com.zx_tole.lineage2_guide.data.repository

import com.zx_tole.lineage2_guide.data.local.dao.ClassesDao
import com.zx_tole.lineage2_guide.data.mapper.ClassMapper
import com.zx_tole.lineage2_guide.data.network.Lineage2ApiService
import com.zx_tole.lineage2_guide.domain.model.GameClass
import com.zx_tole.lineage2_guide.domain.repository.ClassesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class ClassesRepositoryImpl(
    private val classesDao: ClassesDao,
    private val apiService: Lineage2ApiService,
    private val classMapper: ClassMapper
) : ClassesRepository {

    override fun getClasses(race: String?, searchQuery: String?): Flow<List<GameClass>> {
        return classesDao.getClassesByFilter(race, searchQuery)
            .map { entities -> entities.map { classMapper.toDomain(it) } }
            .catch { e ->
                Timber.e(e, "Error fetching classes from cache")
                emit(emptyList())
            }
            .flowOn(Dispatchers.IO)
    }

    override fun getClassById(id: Long): Flow<GameClass?> {
        return classesDao.getClassById(id)
            .map { entity -> entity?.let { classMapper.toDomain(it) } }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun refreshClasses() = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getClasses()
            val entities = response.classes.map { classMapper.toEntity(it) }
            classesDao.upsertClasses(entities)
            Timber.d("Classes refreshed: ${entities.size} classes")
        } catch (e: Exception) {
            Timber.e(e, "Failed to refresh classes")
        }
    }

    override suspend fun clearCache() = withContext(Dispatchers.IO) {
        classesDao.clearClasses()
    }
}
