package ru.practicum.android.microhh.country.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.resources.CountrySearchState
import ru.practicum.android.microhh.country.domain.api.CountrySearchRepository

class CountrySearchUseCase(
    private val repository: CountrySearchRepository
) {

    operator fun invoke(): Flow<CountrySearchState> {
        return repository.getCountries()
    }
}
