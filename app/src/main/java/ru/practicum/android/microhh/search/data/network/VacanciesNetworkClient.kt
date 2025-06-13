package ru.practicum.android.microhh.search.data.network

import ru.practicum.android.microhh.core.data.dto.Response
import ru.practicum.android.microhh.search.data.dto.RetrofitSearchRequest

interface VacanciesNetworkClient {
    suspend fun getVacancies(dto: RetrofitSearchRequest): Response
}
