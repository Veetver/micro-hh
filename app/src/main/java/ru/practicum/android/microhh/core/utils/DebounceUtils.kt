package ru.practicum.android.microhh.core.utils

import android.os.Handler
import android.os.Looper

object DebounceUtils {
    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null

    fun debounce(delayMs: Long, action: () -> Unit) {
        runnable?.let { handler.removeCallbacks(it) }
        runnable = Runnable { action() }
        handler.postDelayed(runnable!!, delayMs)
    }
}
