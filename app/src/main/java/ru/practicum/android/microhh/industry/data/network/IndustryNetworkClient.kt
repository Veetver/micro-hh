package ru.practicum.android.microhh.industry.data.network

import ru.practicum.android.microhh.core.data.dto.Response

interface IndustryNetworkClient {
    suspend fun getIndustries(): Response
}
