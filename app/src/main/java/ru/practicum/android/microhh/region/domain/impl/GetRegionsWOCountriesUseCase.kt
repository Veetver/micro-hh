package ru.practicum.android.microhh.region.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.resources.AreaSearchState
import ru.practicum.android.microhh.region.domain.api.RegionRepository

class GetRegionsWOCountriesUseCase(
    private val repository: RegionRepository
) {

    operator fun invoke(): Flow<AreaSearchState> {
        return repository.getRegions()
    }
}
