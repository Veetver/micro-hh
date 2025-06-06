package ru.practicum.android.microhh.core.utils

import android.content.Context
import android.util.TypedValue
import ru.practicum.android.microhh.filters.domain.model.FilterSettings

object Extensions {

    fun Number.dpToPx(context: Context) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()

    fun FilterSettings.isEmpty(): Boolean {
        return this.workplace.isEmpty() &&
            this.industry.isEmpty() &&
            this.salary.isEmpty() &&
            this.showWithoutSalary.not()
    }
}
