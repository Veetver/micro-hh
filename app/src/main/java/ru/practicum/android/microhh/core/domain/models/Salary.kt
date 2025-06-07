package ru.practicum.android.microhh.core.domain.models

data class Salary(
    val to: Int?,
    val from: Int?,
    val currency: String,
    val gross: Boolean
)
