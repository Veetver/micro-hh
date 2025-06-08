package ru.practicum.android.microhh.core.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.microhh.core.domain.models.Area
import ru.practicum.android.microhh.core.domain.models.AreaExtended
import retrofit2.http.QueryMap
import ru.practicum.android.microhh.industry.data.dto.IndustryDto
import ru.practicum.android.microhh.search.data.dto.VacancyResponse
import ru.practicum.android.microhh.vacancy.data.dto.VacancyDetailsResponse

interface HhService {

    @GET("/vacancies")
    suspend fun searchVacancies(
        @QueryMap options: Map<String, String>,
    ): VacancyResponse

    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyDetails(
        @Path("vacancy_id") id: String
    ): VacancyDetailsResponse

    @GET("/industries")
    suspend fun getIndustries(): List<IndustryDto>

    @GET("/areas/countries")
    suspend fun getCountries(): List<Area>

    @GET("/areas")
    suspend fun getAreas(): List<AreaExtended>

    @GET("/areas/{area_id}")
    suspend fun getAreaById(
        @Path("area_id") id: String
    ): AreaExtended
}
