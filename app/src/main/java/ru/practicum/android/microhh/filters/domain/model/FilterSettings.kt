package ru.practicum.android.microhh.filters.domain.model

data class FilterSettings(
    val workplace: String = "",
    val industry: String = "",
    val salary: String = "",
    val showWithoutSalary: Boolean = false,
)
