package ru.practicum.android.microhh.region.data.network

import ru.practicum.android.microhh.core.data.dto.Response
import ru.practicum.android.microhh.region.data.dto.request.AreaByIdRequest

interface RegionNetworkClient {
    suspend fun getAreas(): Response
    suspend fun getAreaById(dto: AreaByIdRequest): Response
}
