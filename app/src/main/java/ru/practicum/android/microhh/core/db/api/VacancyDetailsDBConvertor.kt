package ru.practicum.android.microhh.core.db.api

import ru.practicum.android.microhh.core.db.VacancyDetailsEntity
import ru.practicum.android.microhh.core.domain.models.Vacancy
import ru.practicum.android.microhh.core.domain.models.VacancyDetails

interface VacancyDetailsDBConvertor {
    fun toVacancyDetailsEntity(vacancy: VacancyDetails): VacancyDetailsEntity
    fun toVacancyDetails(vacancy: VacancyDetailsEntity?): VacancyDetails?
    fun toVacancy(vacancy: VacancyDetailsEntity): Vacancy
}
