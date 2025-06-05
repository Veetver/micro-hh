package ru.practicum.android.microhh.core.data.dto

import ru.practicum.android.microhh.core.domain.models.Vacancy
import ru.practicum.android.microhh.core.domain.models.VacancyDetails
import ru.practicum.android.microhh.search.data.dto.VacancyDto
import ru.practicum.android.microhh.vacancy.data.dto.VacancyDetailsDto
import ru.practicum.android.microhh.vacancy.data.dto.VacancyDetailsResponse

interface VacancyDtoConverter {
    fun toVacancyList(list: List<VacancyDto>): List<Vacancy>
    fun fromVacancyDetailsToVacancyList(list: List<VacancyDetails>): List<Vacancy>
    fun toVacancyDetails(vacancy: VacancyDetailsDto): VacancyDetails
    fun toVacancyDetails(vacancy: VacancyDetailsResponse): VacancyDetails
}
