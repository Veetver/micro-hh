package ru.practicum.android.microhh.vacancy.presentation.models

data class VacancyDetailsUi(
    val url: String,
    val description: String?,
    val experience: String?,
    val companyName: String,
    val title: String,
    val salaryDisplayText: String,
    val companyLogo: String?,
    val keySkills: String?,
    val region: String?,
    val workFormats: String?,
)
