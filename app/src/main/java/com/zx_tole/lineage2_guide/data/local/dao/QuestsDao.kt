package com.zx_tole.lineage2_guide.data.local.dao

import androidx.room.*
import com.zx_tole.lineage2_guide.data.local.entity.QuestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertQuests(quests: List<QuestEntity>)

    @Query(
        "SELECT * FROM quests " +
        "WHERE (:type IS NULL OR type = :type) " +
        "AND (:startLevel IS NULL OR startLevel >= :startLevel) " +
        "AND (:npcId IS NULL OR npcId = :npcId) " +
        "ORDER BY startLevel ASC " +
        "LIMIT :pageSize OFFSET :offset"
    )
    fun getQuestsByFilter(
        type: String?,
        startLevel: Int?,
        npcId: Long?,
        pageSize: Int,
        offset: Int
    ): Flow<List<QuestEntity>>

    @Query("SELECT * FROM quests WHERE id = :id")
    fun getQuestById(id: Long): Flow<QuestEntity?>

    @Query("SELECT * FROM quests")
    fun getAllQuests(): Flow<List<QuestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertQuest(quest: QuestEntity)

    @Query("DELETE FROM quests")
    suspend fun clearQuests()
}
