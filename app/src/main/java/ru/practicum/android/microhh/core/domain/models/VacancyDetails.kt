package ru.practicum.android.microhh.core.domain.models

data class VacancyDetails(
    val id: Long,
    val name: String,
    val areaName: String,
    val employerName: String,
    val salaryDisplayText: String,
    val experience: String,
    val employment: String?,
    val workFormat: String?,
    val description: String,
    val keySkills: String?,
    val employerLogo: String?,
    val url: String
)
