package ru.practicum.android.microhh.country.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.resources.AreaSearchState
import ru.practicum.android.microhh.country.domain.api.CountryRepository

class GetCountriesUseCase(
    private val repository: CountryRepository
) {

    operator fun invoke(): Flow<AreaSearchState> {
        return repository.getCountries()
    }
}
