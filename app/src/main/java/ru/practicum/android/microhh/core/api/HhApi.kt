package ru.practicum.android.microhh.core.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.practicum.android.microhh.core.models.VacancyResponse

interface HhApi {
    @GET("/vacancies")
    fun vacancies(@Query("text") text: String): Call<VacancyResponse>
}
