package ru.practicum.android.microhh.country.data.network

import ru.practicum.android.microhh.core.data.dto.Response

interface CountryNetworkClient {
    suspend fun getCountries(): Response
}
