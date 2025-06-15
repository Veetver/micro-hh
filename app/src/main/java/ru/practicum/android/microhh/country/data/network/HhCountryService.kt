package ru.practicum.android.microhh.country.data.network

import retrofit2.http.GET
import ru.practicum.android.microhh.core.domain.models.Area

interface HhCountryService {

    @GET("/areas/countries")
    suspend fun getCountries(): List<Area>
}
