package ru.practicum.android.microhh.vacancy.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.vacancy.data.VacancyDetailsState

interface VacancyDetailsRepository {
    fun searchVacancy(term: String): Flow<VacancyDetailsState>
}

