package ru.practicum.android.microhh.core.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.microhh.search.data.dto.VacancyResponse
import ru.practicum.android.microhh.vacancy.data.dto.VacancyDetailsResponse

interface HhService {
    @GET("/vacancies")
    suspend fun searchVacancies(
        @Query("text") text: String,
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int = 1,
    ): VacancyResponse

    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyDetails(
        @Path("vacancy_id") id: String
    ): VacancyDetailsResponse
}
