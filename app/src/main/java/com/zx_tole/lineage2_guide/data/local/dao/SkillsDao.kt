package com.zx_tole.lineage2_guide.data.local.dao

import androidx.room.*
import com.zx_tole.lineage2_guide.data.local.entity.SkillEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SkillsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSkills(skills: List<SkillEntity>)

    @Query(
        "SELECT * FROM skills " +
        "WHERE (:classRestriction IS NULL OR classRestriction = :classRestriction) " +
        "AND (:type IS NULL OR type = :type) " +
        "AND (:minLevel IS NULL OR level >= :minLevel) " +
        "AND (:maxLevel IS NULL OR level <= :maxLevel) " +
        "AND (:searchQuery IS NULL OR name LIKE '%' || :searchQuery || '%') " +
        "ORDER BY level ASC " +
        "LIMIT :pageSize OFFSET :offset"
    )
    fun getSkillsByFilter(
        classRestriction: String?,
        type: String?,
        minLevel: Int?,
        maxLevel: Int?,
        searchQuery: String?,
        pageSize: Int,
        offset: Int
    ): Flow<List<SkillEntity>>

    @Query("SELECT * FROM skills WHERE id = :id")
    fun getSkillById(id: Long): Flow<SkillEntity?>

    @Query("SELECT * FROM skills")
    fun getAllSkills(): Flow<List<SkillEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSkill(skill: SkillEntity)

    @Query("DELETE FROM skills")
    suspend fun clearSkills()
}
