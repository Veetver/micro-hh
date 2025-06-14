package ru.practicum.android.microhh.region.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.microhh.core.data.dto.Response
import ru.practicum.android.microhh.core.utils.Constants
import ru.practicum.android.microhh.core.utils.Network
import ru.practicum.android.microhh.region.data.dto.request.AreaByIdRequest
import ru.practicum.android.microhh.region.data.dto.response.AreaExtendedResponse
import ru.practicum.android.microhh.region.data.dto.response.AreasResponse
import ru.practicum.android.microhh.region.data.network.HhRegionService
import ru.practicum.android.microhh.region.data.network.RegionNetworkClient

class RegionNetworkClientImpl(
    private val hhService: HhRegionService,
    private val network: Network,
) : RegionNetworkClient {

    override suspend fun getAreas(): Response {
        return withContext(Dispatchers.IO) {
            network.doRequest {
                val response = AreasResponse(areas = hhService.getAreas())
                response.apply {
                    resultCode = Constants.HTTP_OK
                }
            }
        }
    }

    override suspend fun getAreaById(dto: AreaByIdRequest): Response {
        return withContext(Dispatchers.IO) {
            network.doRequest {
                val response = hhService.getAreaById(dto.id)
                val result = AreaExtendedResponse(
                    areas = response.areas,
                    id = response.id,
                    name = response.name,
                    parentId = response.parentId,
                )
                result.apply {
                    resultCode = Constants.HTTP_OK
                }
            }
        }
    }
}
