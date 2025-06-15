package ru.practicum.android.microhh.industry.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.microhh.core.data.dto.Response
import ru.practicum.android.microhh.core.utils.NetworkErrorHandler
import ru.practicum.android.microhh.industry.data.dto.IndustryResponse
import ru.practicum.android.microhh.industry.data.network.HhIndustryService
import ru.practicum.android.microhh.industry.data.network.IndustryNetworkClient

class IndustryNetworkClientImpl(
    private val hhService: HhIndustryService,
    private val network: NetworkErrorHandler,
) : IndustryNetworkClient {

    override suspend fun getIndustries(): Response {
        return withContext(Dispatchers.IO) {
            network.doRequest {
                val catalog = hhService.getIndustries()
                IndustryResponse(catalog)
            }
        }
    }
}
