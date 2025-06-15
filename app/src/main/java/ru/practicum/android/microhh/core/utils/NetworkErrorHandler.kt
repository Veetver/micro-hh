package ru.practicum.android.microhh.core.utils

import retrofit2.HttpException
import ru.practicum.android.microhh.core.data.dto.Response
import java.io.IOException

class NetworkErrorHandler(
    val networkCheck: NetworkCheck
) {

    inline fun doRequest(action: () -> Response): Response {
        return if (networkCheck.isNetworkAvailable()) {
            try {
                action().apply {
                    resultCode = Constants.HTTP_OK
                }
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
