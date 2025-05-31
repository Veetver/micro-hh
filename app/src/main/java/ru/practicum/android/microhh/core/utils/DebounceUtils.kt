package ru.practicum.android.microhh.core.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Debounce<T>(
    private val delay: Long = 0,
    private val scope: CoroutineScope,
    private val action: suspend (T?) -> Unit
) {

    private var job: Job? = null

    fun start(parameter: T? = null) {
        job?.cancel()
        job = scope.launch {
            delay(delay)
            action(parameter)
        }
    }
}
