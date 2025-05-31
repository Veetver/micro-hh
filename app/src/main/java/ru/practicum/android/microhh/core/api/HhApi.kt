package ru.practicum.android.microhh.core.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.practicum.android.microhh.core.models.VacancyResponse

interface HhApi {
    @GET("/vacancies")
    suspend fun vacancies(
        @Query("text") text: String,
        @Header("Authorization") token: String
    ): VacancyResponse
}
