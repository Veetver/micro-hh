package ru.practicum.android.microhh.core.domain.models

data class VacancyDetails(
    val id: String,
    val title: String,
    val salary: Salary?,
    val companyLogo: LogoUrls?,
    val companyName: String,
    val area: Area,
    val experience: String?,
    val workFormats: List<WorkFormat>?,
    val description: String?,
    val keySkills: List<KeySkills>?,
    val url: String,
)
