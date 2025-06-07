package ru.practicum.android.microhh.country.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.resources.CountrySearchState

interface CountrySearchRepository {
    fun getCountries(): Flow<CountrySearchState>
}
