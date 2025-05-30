package ru.practicum.android.microhh.core.utils

import android.content.Context
import android.util.TypedValue

object Extensions {

    fun Number.dpToPx(context: Context) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}
