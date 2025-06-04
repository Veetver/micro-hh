package ru.practicum.android.microhh.vacancy.data.dto

import ru.practicum.android.microhh.core.domain.models.VacancyDetails

fun VacancyDetailsResponse.toVacancyDetails() = VacancyDetails(
    id = id,
    title = name,
    salary = salary,
    companyLogo = employer.logoUrls,
    companyName = employer.name,
    area = area,
    experience = experience.name,
    workFormats = workFormat,
    description = description,
    keySkills = keySkills,
    url = alternateUrl,
)
