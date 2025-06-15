package ru.practicum.android.microhh.search.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.microhh.core.data.dto.Response
import ru.practicum.android.microhh.core.utils.NetworkErrorHandler
import ru.practicum.android.microhh.search.data.dto.RetrofitSearchRequest
import ru.practicum.android.microhh.search.data.network.HhVacanciesService
import ru.practicum.android.microhh.search.data.network.VacanciesNetworkClient

class VacanciesNetworkClientImpl(
    private val hhService: HhVacanciesService,
    private val network: NetworkErrorHandler,
) : VacanciesNetworkClient {

    override suspend fun getVacancies(dto: RetrofitSearchRequest): Response {
        return withContext(Dispatchers.IO) {
            network.doRequest {
                hhService.searchVacancies(dto.options)
            }
        }
    }
}
