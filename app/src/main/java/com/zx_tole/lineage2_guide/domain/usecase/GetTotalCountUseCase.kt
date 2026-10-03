package com.zx_tole.lineage2_guide.domain.usecase

import com.zx_tole.lineage2_guide.domain.model.ItemsFilter
import com.zx_tole.lineage2_guide.domain.repository.ItemsRepository

class GetTotalCountUseCase(
    private val repository: ItemsRepository
) {
    suspend operator fun invoke(filter: ItemsFilter): Int {
        return repository.getTotalCount(filter)
    }
}
