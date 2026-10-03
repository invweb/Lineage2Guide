package com.zx_tole.lineage2_guide.data.local.dao

import androidx.room.*
import com.zx_tole.lineage2_guide.data.local.entity.ClassEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertClasses(classes: List<ClassEntity>)

    @Query(
        "SELECT * FROM classes " +
        "WHERE (:race IS NULL OR race = :race) " +
        "AND (:searchQuery IS NULL OR name LIKE '%' || :searchQuery || '%')"
    )
    fun getClassesByFilter(
        race: String?,
        searchQuery: String?
    ): Flow<List<ClassEntity>>

    @Query("SELECT * FROM classes WHERE id = :id")
    fun getClassById(id: Long): Flow<ClassEntity?>

    @Query("SELECT * FROM classes")
    fun getAllClasses(): Flow<List<ClassEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertClass(classEntity: ClassEntity)

    @Query("DELETE FROM classes")
    suspend fun clearClasses()
}
