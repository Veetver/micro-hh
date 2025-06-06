package ru.practicum.android.microhh.region.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.resources.AreaExtendedSearchState
import ru.practicum.android.microhh.core.resources.AreaSearchState

interface RegionRepository {
    fun getRegions(): Flow<AreaSearchState>
    fun getRegionById(id: String): Flow<AreaExtendedSearchState>
}
