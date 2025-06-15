package ru.practicum.android.microhh.country.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.microhh.core.data.dto.Response
import ru.practicum.android.microhh.core.utils.NetworkErrorHandler
import ru.practicum.android.microhh.country.data.dto.response.CountriesResponse
import ru.practicum.android.microhh.country.data.network.CountryNetworkClient
import ru.practicum.android.microhh.country.data.network.HhCountryService

class CountryNetworkClientImpl(
    private val hhService: HhCountryService,
    private val network: NetworkErrorHandler,
) : CountryNetworkClient {

    override suspend fun getCountries(): Response {
        return withContext(Dispatchers.IO) {
            network.doRequest {
                CountriesResponse(areas = hhService.getCountries())
            }
        }
    }
}
