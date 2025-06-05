package ru.practicum.android.microhh.vacancy.presentation.models

import ru.practicum.android.microhh.core.domain.models.JobInfo

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
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val currency: String?,
)

fun VacancyDetailsUi.toJobInfo(id: Long = 0L): JobInfo =
    JobInfo(
        id = id,
        name = title,
        areaName = region ?: "",
        employerName = companyName,
        salaryFrom = salaryFrom,
        salaryTo = salaryTo,
        currency = currency ?: "",
        experience = experience ?: "",
        employmentFormName = null,
        workFormatName = workFormats ?: "",
        description = description ?: "",
        keySkills = keySkills ?: "",
        employerLogo = companyLogo
    )
