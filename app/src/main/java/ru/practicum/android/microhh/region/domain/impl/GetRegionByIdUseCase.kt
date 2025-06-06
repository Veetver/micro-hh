package ru.practicum.android.microhh.region.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.resources.AreaExtendedSearchState
import ru.practicum.android.microhh.region.domain.api.RegionRepository

class GetRegionByIdUseCase(
    private val repository: RegionRepository
) {

    operator fun invoke(id: String): Flow<AreaExtendedSearchState> {
        return repository.getRegionById(id)
    }
}
