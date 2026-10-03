package com.zx_tole.lineage2_guide.domain.usecase

import com.zx_tole.lineage2_guide.domain.model.Item
import com.zx_tole.lineage2_guide.domain.model.ItemsFilter
import com.zx_tole.lineage2_guide.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.Flow

class GetItemsUseCase(
    private val repository: ItemsRepository
) {
    operator fun invoke(filter: ItemsFilter): Flow<List<Item>> {
        return repository.getItems(filter)
    }
}
