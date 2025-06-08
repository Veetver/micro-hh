package ru.practicum.android.microhh.filters.domain.model

data class FilterSettings(
    val areaId: String = "",
    val areaName: String = "",
    val industryId: String = "",
    val industryName: String = "",
    val salary: String = "",
    val showWithoutSalary: Boolean = false,
)
