package ru.practicum.android.microhh.search.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.microhh.core.data.dto.Response
import ru.practicum.android.microhh.core.utils.Constants
import ru.practicum.android.microhh.core.utils.Network
import ru.practicum.android.microhh.search.data.dto.RetrofitSearchRequest
import ru.practicum.android.microhh.search.data.network.HhVacanciesService
import ru.practicum.android.microhh.search.data.network.VacanciesNetworkClient

class VacanciesNetworkClientImpl(
    private val hhService: HhVacanciesService,
    private val network: Network,
) : VacanciesNetworkClient {

    override suspend fun getVacancies(dto: RetrofitSearchRequest): Response {
        return withContext(Dispatchers.IO) {
            network.doRequest {
                val response = hhService.searchVacancies(dto.options)
                response.apply {
                    resultCode = Constants.HTTP_OK
                }
            }
        }
    }
}
