package ru.practicum.android.microhh.search.data.network

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.practicum.android.microhh.search.data.dto.VacancyResponse

interface HhVacanciesService {

    @GET("/vacancies")
    suspend fun searchVacancies(
        @QueryMap options: Map<String, String>
    ): VacancyResponse
}

