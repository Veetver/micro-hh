package ru.practicum.android.microhh.vacancy.data.dto

import ru.practicum.android.microhh.core.models.items.Vacancy


interface VacancyDetailsDtoConverter {
    fun toVacancy(vacancyDetailsDto: VacancyDetailsDto): Vacancy
}
