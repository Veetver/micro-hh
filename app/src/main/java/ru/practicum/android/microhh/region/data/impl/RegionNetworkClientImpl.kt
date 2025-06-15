package ru.practicum.android.microhh.region.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.microhh.core.data.dto.Response
import ru.practicum.android.microhh.core.utils.NetworkErrorHandler
import ru.practicum.android.microhh.region.data.dto.request.AreaByIdRequest
import ru.practicum.android.microhh.region.data.dto.response.AreaExtendedResponse
import ru.practicum.android.microhh.region.data.dto.response.AreasResponse
import ru.practicum.android.microhh.region.data.network.HhRegionService
import ru.practicum.android.microhh.region.data.network.RegionNetworkClient

class RegionNetworkClientImpl(
    private val hhService: HhRegionService,
    private val network: NetworkErrorHandler,
) : RegionNetworkClient {

    override suspend fun getAreas(): Response {
        return withContext(Dispatchers.IO) {
            network.doRequest {
                AreasResponse(areas = hhService.getAreas())
            }
        }
    }

    override suspend fun getAreaById(dto: AreaByIdRequest): Response {
        return withContext(Dispatchers.IO) {
            network.doRequest {
                val response = hhService.getAreaById(dto.id)
                AreaExtendedResponse(
                    areas = response.areas,
                    id = response.id,
                    name = response.name,
                    parentId = response.parentId,
                )
            }
        }
    }
}
