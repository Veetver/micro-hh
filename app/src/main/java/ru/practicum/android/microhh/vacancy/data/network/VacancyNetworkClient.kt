package ru.practicum.android.microhh.vacancy.data.network

import ru.practicum.android.microhh.core.data.dto.Response
import ru.practicum.android.microhh.vacancy.data.dto.RetrofitVacancyDetailsRequest

interface VacancyNetworkClient {
    suspend fun getVacancy(dto: RetrofitVacancyDetailsRequest): Response
}
