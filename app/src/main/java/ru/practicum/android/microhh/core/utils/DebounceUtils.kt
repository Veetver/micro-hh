package ru.practicum.android.microhh.core.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DebounceUtils {
    private var job: Job? = null

    fun debounce(
        delayMillis: Long = 0,
        scope: CoroutineScope,
        action: suspend () -> Unit
    ) {
        job?.cancel()
        job = scope.launch {
            delay(delayMillis)
            action()
        }
    }
}
