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
                handleError(Constants.INTERNAL_CLIENT_ERROR, e)
            } catch (e: HttpException) {
                handleError(Constants.INTERNAL_SERVER_ERROR, e)
            }
        } else {
            handleError(Constants.NO_CONNECTION)
        }
    }

    fun handleError(code: Int, error: Exception? = null): Response {
        error?.let {
            AppLog.d(AppLog.RETROFIT_API_RESPONSE, AppLog.getStackTraceString(it))
        }

        return Response().apply {
            resultCode = code
        }
    }
}
