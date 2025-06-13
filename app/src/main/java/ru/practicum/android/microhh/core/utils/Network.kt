package ru.practicum.android.microhh.core.utils

import retrofit2.HttpException
import ru.practicum.android.microhh.core.data.dto.Response
import ru.practicum.android.microhh.core.data.network.NetworkCheck
import java.io.IOException

class Network(
    val networkCheck: NetworkCheck,
) {

    inline fun doRequest(action: () -> Response): Response {
        return if (networkCheck.isNetworkAvailable()) {
            try {
                action()
            } catch (e: IOException) {
                AppLog.d(AppLog.RETROFIT_API_RESPONSE, AppLog.getStackTraceString(e))
                Response().apply {
                    resultCode = Constants.INTERNAL_CLIENT_ERROR
                }
            } catch (e: HttpException) {
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
