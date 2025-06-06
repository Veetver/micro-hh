package ru.practicum.android.microhh.country.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.resources.AreaSearchState

interface CountryRepository {
    fun getCountries(): Flow<AreaSearchState>
}
