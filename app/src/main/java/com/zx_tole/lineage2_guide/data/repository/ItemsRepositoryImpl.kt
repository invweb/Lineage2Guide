package com.zx_tole.lineage2_guide.data.repository

import com.zx_tole.lineage2_guide.data.local.dao.ItemsDao
import com.zx_tole.lineage2_guide.data.mapper.ItemMapper
import com.zx_tole.lineage2_guide.data.network.Lineage2ApiService
import com.zx_tole.lineage2_guide.domain.model.Item
import com.zx_tole.lineage2_guide.domain.model.ItemsFilter
import com.zx_tole.lineage2_guide.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class ItemsRepositoryImpl(
    private val itemsDao: ItemsDao,
    private val apiService: Lineage2ApiService,
    private val itemMapper: ItemMapper
) : ItemsRepository {

    override fun getItems(filter: ItemsFilter): Flow<List<Item>> {
        val offset = filter.page * filter.pageSize
        return itemsDao.getItemsByFilter(
            classRestriction = filter.classRestriction,
            minLevel = filter.minLevel,
            maxLevel = filter.maxLevel,
            type = filter.type,
            rarity = filter.rarity,
            location = filter.location,
            searchQuery = filter.searchQuery,
            pageSize = filter.pageSize,
            offset = offset
        )
            .map { entities -> entities.map { itemMapper.toDomain(it) } }
            .catch { e ->
                Timber.e(e, "Error fetching items from cache")
                emit(emptyList())
            }
            .flowOn(Dispatchers.IO)
    }

    override fun getItemById(id: Long): Flow<Item?> {
        return itemsDao.getItemById(id)
            .map { entity -> entity?.let { itemMapper.toDomain(it) } }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun refreshItems() = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getItems()
            val entities = response.items.map { itemMapper.toEntity(it) }
            itemsDao.upsertItems(entities)
            Timber.d("Items refreshed: ${entities.size} items")
        } catch (e: Exception) {
            Timber.e(e, "Failed to refresh items")
        }
    }

    override suspend fun clearCache() = withContext(Dispatchers.IO) {
        itemsDao.clearItems()
    }
}
