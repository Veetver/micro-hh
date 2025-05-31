package ru.practicum.android.microhh.core.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.practicum.android.microhh.search.data.dto.VacancyResponse

interface HhService {

    @GET("/vacancies")
    suspend fun searchVacancies(
        @Query("text") text: String,
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int = 1,
    ): VacancyResponse
}
