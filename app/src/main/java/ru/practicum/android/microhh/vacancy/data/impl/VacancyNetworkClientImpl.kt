package ru.practicum.android.microhh.vacancy.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.microhh.core.data.dto.Response
import ru.practicum.android.microhh.core.utils.NetworkErrorHandler
import ru.practicum.android.microhh.vacancy.data.dto.RetrofitVacancyDetailsRequest
import ru.practicum.android.microhh.vacancy.data.network.HhVacancyService
import ru.practicum.android.microhh.vacancy.data.network.VacancyNetworkClient

class VacancyNetworkClientImpl(
    private val hhService: HhVacancyService,
    private val network: NetworkErrorHandler,
) : VacancyNetworkClient {

    override suspend fun getVacancy(dto: RetrofitVacancyDetailsRequest): Response {
        return withContext(Dispatchers.IO) {
            network.doRequest {
                hhService.getVacancyDetails(id = dto.id)
            }
        }
    }
}
