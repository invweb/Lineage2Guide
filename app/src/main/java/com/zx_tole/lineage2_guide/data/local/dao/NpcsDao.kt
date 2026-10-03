package com.zx_tole.lineage2_guide.data.local.dao

import androidx.room.*
import com.zx_tole.lineage2_guide.data.local.entity.NpcEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NpcsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNpcs(npcs: List<NpcEntity>)

    @Query(
        "SELECT * FROM npcs " +
        "WHERE (:type IS NULL OR type = :type) " +
        "AND (:location IS NULL OR location = :location) " +
        "AND (:searchQuery IS NULL OR name LIKE '%' || :searchQuery || '%')"
    )
    fun getNpcsByFilter(
        type: String?,
        location: String?,
        searchQuery: String?
    ): Flow<List<NpcEntity>>

    @Query("SELECT * FROM npcs WHERE id = :id")
    fun getNpcById(id: Long): Flow<NpcEntity?>

    @Query("SELECT * FROM npcs")
    fun getAllNpcs(): Flow<List<NpcEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNpc(npc: NpcEntity)

    @Query("DELETE FROM npcs")
    suspend fun clearNpcs()
}
