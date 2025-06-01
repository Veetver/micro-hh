package ru.practicum.android.microhh.core.domain.models

data class VacancyDetails(
    val id: String,
    val companyLogo: String?,
    val title: String,
    val companyName: String,
    val salaryDisplayText: String,
    val experience: String?,
    val employment: String?,
    val region: String?,
    val industry: String?
)
