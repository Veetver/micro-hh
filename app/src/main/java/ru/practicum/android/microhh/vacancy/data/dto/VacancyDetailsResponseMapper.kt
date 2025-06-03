package ru.practicum.android.microhh.vacancy.data.dto

import ru.practicum.android.microhh.core.models.items.Vacancy

fun VacancyDetailsResponse.toVacancy() = Vacancy(
    id = id,
    salary = salary,
    name = name,
    area = area,
    url = alternateUrl,
    employer = employer,
    experience = experience,
    employment = employment,
    addressCity = address?.city,
    workFormat = workFormat,
    description = description,
    keySkills = keySkills
)
