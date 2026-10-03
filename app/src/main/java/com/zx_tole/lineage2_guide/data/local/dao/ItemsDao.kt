package com.zx_tole.lineage2_guide.data.local.dao

import androidx.room.*
import com.zx_tole.lineage2_guide.data.local.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertItems(items: List<ItemEntity>)

    @Query(
        "SELECT * FROM items " +
        "WHERE (:classRestriction IS NULL OR classRestriction = :classRestriction) " +
        "AND (:minLevel IS NULL OR level >= :minLevel) " +
        "AND (:maxLevel IS NULL OR level <= :maxLevel) " +
        "AND (:type IS NULL OR type = :type) " +
        "AND (:rarity IS NULL OR rarity = :rarity) " +
        "AND (:location IS NULL OR location = :location) " +
        "AND (:searchQuery IS NULL OR name LIKE '%' || :searchQuery || '%') " +
        "ORDER BY level ASC, name ASC " +
        "LIMIT :pageSize OFFSET :offset"
    )
    fun getItemsByFilter(
        classRestriction: String?,
        minLevel: Int?,
        maxLevel: Int?,
        type: String?,
        rarity: String?,
        location: String?,
        searchQuery: String?,
        pageSize: Int,
        offset: Int
    ): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items WHERE id = :id")
    fun getItemById(id: Long): Flow<ItemEntity?>

    @Query("SELECT * FROM items ORDER BY level ASC")
    fun getAllItems(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items WHERE name LIKE '%' || :query || '%'")
    fun searchItems(query: String): Flow<List<ItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertItem(item: ItemEntity)

    @Query("DELETE FROM items")
    suspend fun clearItems()
}
