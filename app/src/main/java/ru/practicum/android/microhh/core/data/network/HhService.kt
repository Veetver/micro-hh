package ru.practicum.android.microhh.core.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.microhh.search.data.dto.VacancyResponse
import ru.practicum.android.microhh.vacancy.data.dto.VacancyDetailsResponse

interface HhService {
    @Headers("Authorization: Bearer hhAccessToken",
        "HH-User-Agent: mirco hh (HH_EMAIL)")
    @GET("/vacancies")
    suspend fun searchVacancies(
        @Query("text") text: String,
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int = 1,
    ): VacancyResponse

    @Headers("Authorization: Bearer hhAccessToken",
        "HH-User-Agent: mirco hh (HH_EMAIL)")
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyDetails(
        @Path("vacancy_id") id: String
    ): VacancyDetailsResponse
}
