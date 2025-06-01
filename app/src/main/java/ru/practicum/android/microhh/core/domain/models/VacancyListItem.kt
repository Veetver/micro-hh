package ru.practicum.android.microhh.core.domain.models

data class VacancyListItem(
    val id: String,
    val companyLogo: String,
    val title: String,
    val companyName: String,
    val salaryDisplayText: String,
)
