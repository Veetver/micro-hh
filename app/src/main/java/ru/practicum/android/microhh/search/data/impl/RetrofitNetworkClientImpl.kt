package ru.practicum.android.microhh.search.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.microhh.core.data.dto.Response
import ru.practicum.android.microhh.core.data.network.HhService
import ru.practicum.android.microhh.core.data.network.NetworkCheck
import ru.practicum.android.microhh.core.data.network.RetrofitNetworkClient
import ru.practicum.android.microhh.core.utils.AppLog
import ru.practicum.android.microhh.core.utils.Constants
import ru.practicum.android.microhh.region.data.dto.AreaRequest
import ru.practicum.android.microhh.search.data.dto.RetrofitSearchRequest
import ru.practicum.android.microhh.vacancy.data.dto.RetrofitVacancyDetailsRequest
import java.io.IOException

class RetrofitNetworkClientImpl(
    private val hhService: HhService,
    private val networkCheck: NetworkCheck,
) : RetrofitNetworkClient {

    override suspend fun doRequest(dto: RetrofitSearchRequest): Response {
        return withContext(Dispatchers.IO) {
            if (networkCheck.isNetworkAvailable()) {
                try {
                    val response = hhService.searchVacancies(text = dto.term, page = dto.page)
                    response.apply {
                        resultCode = Constants.HTTP_OK
                    }
                } catch (e: IOException) {
                    AppLog.d(AppLog.RETROFIT_API_RESPONSE, AppLog.getStackTraceString(e))
                    Response().apply {
                        resultCode = Constants.INTERNAL_SERVER_ERROR
                    }
                }
            } else {
                Response().apply {
                    resultCode = Constants.NO_CONNECTION
                }
            }
        }
    }

    override suspend fun getVacancy(dto: RetrofitVacancyDetailsRequest): Response {
        return withContext(Dispatchers.IO) {
            if (networkCheck.isNetworkAvailable()) {
                try {
                    val response = hhService.getVacancyDetails(id = dto.id)
                    response.apply {
                        resultCode = Constants.HTTP_OK
                    }
                } catch (e: IOException) {
                    AppLog.d(AppLog.RETROFIT_API_RESPONSE, AppLog.getStackTraceString(e))
                    Response().apply {
                        resultCode = Constants.INTERNAL_SERVER_ERROR
                    }
                }
            } else {
                Response().apply {
                    resultCode = Constants.NO_CONNECTION
                }
            }
        }
    }

    override suspend fun getCountries(): Response = withContext(Dispatchers.IO) {
        if (networkCheck.isNetworkAvailable()) {
            try {
                val response = hhService.getCountries()
                response.apply {
                    resultCode = Constants.HTTP_OK
                }
            } catch (e: IOException) {
                AppLog.d(AppLog.RETROFIT_API_RESPONSE, AppLog.getStackTraceString(e))
                Response().apply {
                    resultCode = Constants.INTERNAL_SERVER_ERROR
                }
            }
        } else {
            Response().apply {
                resultCode = Constants.NO_CONNECTION
            }
        }
    }

    override suspend fun getAreas(): Response = withContext(Dispatchers.IO) {
        if (networkCheck.isNetworkAvailable()) {
            try {
                val response = hhService.getAreas()
                response.apply {
                    resultCode = Constants.HTTP_OK
                }
            } catch (e: IOException) {
                AppLog.d(AppLog.RETROFIT_API_RESPONSE, AppLog.getStackTraceString(e))
                Response().apply {
                    resultCode = Constants.INTERNAL_SERVER_ERROR
                }
            }
        } else {
            Response().apply {
                resultCode = Constants.NO_CONNECTION
            }
        }
    }

    override suspend fun getAreaById(dto: AreaRequest): Response = withContext(Dispatchers.IO) {
        if (networkCheck.isNetworkAvailable()) {
            try {
                val response = hhService.getAreaById(dto.id)
                response.apply {
                    resultCode = Constants.HTTP_OK
                }
            } catch (e: IOException) {
                AppLog.d(AppLog.RETROFIT_API_RESPONSE, AppLog.getStackTraceString(e))
                Response().apply {
                    resultCode = Constants.INTERNAL_SERVER_ERROR
                }
            }
        } else {
            Response().apply {
                resultCode = Constants.NO_CONNECTION
            }
        }
    }
}
