package ru.practicum.android.microhh.industry.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.resources.CatalogState

interface IndustryRepository {
    fun getIndustries(): Flow<CatalogState>
}
