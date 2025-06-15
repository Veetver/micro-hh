package ru.practicum.android.microhh.country.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.microhh.core.resources.AreaSearchState
import ru.practicum.android.microhh.core.utils.Constants
import ru.practicum.android.microhh.country.data.dto.response.CountriesResponse
import ru.practicum.android.microhh.country.data.network.CountryNetworkClient
import ru.practicum.android.microhh.country.domain.api.CountryRepository

class CountryRepositoryImpl(
    private val networkClient: CountryNetworkClient,
) : CountryRepository {

    override fun getCountries(): Flow<AreaSearchState> = flow {
        val response = networkClient.getCountries()

        when (response.resultCode) {
            Constants.HTTP_OK -> {
                when (response) {
                    is CountriesResponse -> {
                        emit(
                            AreaSearchState.Success(areas = response.areas)
                        )
                    }
                }
            }

            else -> {
                emit(AreaSearchState.Error(error = Constants.NO_CONNECTION))
            }
        }
    }
}
