package ru.practicum.android.microhh.industry.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.resources.IndustryState

interface IndustryRepository {
    fun getIndustries(): Flow<IndustryState>
}
