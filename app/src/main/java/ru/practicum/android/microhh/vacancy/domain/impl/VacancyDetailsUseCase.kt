package ru.practicum.android.microhh.vacancy.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.resources.VacancyDetailsState
import ru.practicum.android.microhh.vacancy.domain.api.VacancyDetailsRepository

class VacancyDetailsUseCase(
    private val repository: VacancyDetailsRepository
) {

    operator fun invoke(term: String): Flow<VacancyDetailsState> {
        return repository.getVacancyDetails(term)
    }
}

