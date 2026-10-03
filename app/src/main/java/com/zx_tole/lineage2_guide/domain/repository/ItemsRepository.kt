package com.zx_tole.lineage2_guide.domain.repository

import com.zx_tole.lineage2_guide.domain.model.Item
import com.zx_tole.lineage2_guide.domain.model.ItemsFilter
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    fun getItems(filter: ItemsFilter): Flow<List<Item>>
    suspend fun getTotalCount(filter: ItemsFilter): Int
    fun getItemById(id: Long): Flow<Item?>
    suspend fun refreshItems()
    suspend fun clearCache()
}
