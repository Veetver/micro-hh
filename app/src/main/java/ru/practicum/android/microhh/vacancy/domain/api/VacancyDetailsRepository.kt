package ru.practicum.android.microhh.vacancy.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.resources.VacancyDetailsState

interface VacancyDetailsRepository {
    fun getVacancyDetails(term: String): Flow<VacancyDetailsState>
}

