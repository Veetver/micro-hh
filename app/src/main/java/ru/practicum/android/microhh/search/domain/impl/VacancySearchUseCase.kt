package ru.practicum.android.microhh.search.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.resources.VacancySearchState
import ru.practicum.android.microhh.search.domain.api.VacancySearchRepository

class VacancySearchUseCase(
    private val repository: VacancySearchRepository
) {

    operator fun invoke(term: String, page: Int = 0) : Flow<VacancySearchState> {
        return repository.searchVacancy(term, page)
    }
}
