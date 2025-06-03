package ru.practicum.android.microhh.search.data.dto

import ru.practicum.android.microhh.core.domain.models.Vacancy

interface VacancyDtoConverter {
    fun toVacancyList(list: List<VacancyDto>): List<Vacancy>
}
