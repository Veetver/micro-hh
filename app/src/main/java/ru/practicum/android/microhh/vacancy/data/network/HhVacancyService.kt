package ru.practicum.android.microhh.vacancy.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.practicum.android.microhh.vacancy.data.dto.VacancyDetailsResponse

interface HhVacancyService {

    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyDetails(
        @Path("vacancy_id") id: String
    ): VacancyDetailsResponse
}
