package ru.practicum.android.microhh.vacancy.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.microhh.vacancy.domain.VacancyDetailsRepository

class VacancyDetailsRepositoryImpl : VacancyDetailsRepository {
    override fun searchVacancy(term: String): Flow<VacancyDetailsState> = flow {
    }
}
