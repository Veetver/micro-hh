package ru.practicum.android.microhh.core.data.network

import ru.practicum.android.microhh.core.data.dto.Response
import ru.practicum.android.microhh.region.data.dto.request.AreaByIdRequest
import ru.practicum.android.microhh.search.data.dto.RetrofitSearchRequest
import ru.practicum.android.microhh.vacancy.data.dto.RetrofitVacancyDetailsRequest

interface RetrofitNetworkClient {
    suspend fun doRequest(dto: RetrofitSearchRequest): Response
    suspend fun getVacancy(dto: RetrofitVacancyDetailsRequest): Response
    suspend fun getCountries(): Response
    suspend fun getAreas(): Response
    suspend fun getAreaById(dto: AreaByIdRequest): Response
}
