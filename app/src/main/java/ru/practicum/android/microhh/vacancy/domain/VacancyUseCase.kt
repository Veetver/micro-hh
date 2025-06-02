package ru.practicum.android.microhh.vacancy.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.vacancy.data.VacancyDetailsState

class VacancyUseCase(
    private val repository: VacancyDetailsRepository
) {

    operator fun invoke(term: String): Flow<VacancyDetailsState> {
        return repository.searchVacancy(term)
    }
}

