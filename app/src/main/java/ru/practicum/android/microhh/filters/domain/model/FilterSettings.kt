package ru.practicum.android.microhh.filters.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterSettings(
    val workplace: String = "",
    val industry: String = "",
    val salary: String = "",
    val showWithoutSalary: Boolean = false,
) : Parcelable
