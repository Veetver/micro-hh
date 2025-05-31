package ru.practicum.android.microhh.core.data.network

import ru.practicum.android.microhh.core.data.dto.Response
import ru.practicum.android.microhh.search.data.dto.RetrofitSearchRequest

interface RetrofitNetworkClient {
    suspend fun doRequest(dto: RetrofitSearchRequest): Response
}
