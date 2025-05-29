package ru.practicum.android.microhh.core.utils

import android.util.Log
import ru.practicum.android.microhh.BuildConfig

object AppLog {

    const val RETROFIT_API_RESPONSE = "RETROFIT_API_RESPONSE"
    private val enableLog = BuildConfig.DEBUG

    fun d(tag: String, message: String) {
        if (enableLog) {
            Log.d(tag, message)
        }
    }

    fun e(tag: String, message: String) {
        if (enableLog) {
            Log.e(tag, message)
        }
    }
}
