package ru.practicum.android.microhh.search.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.resources.VacancySearchState
import ru.practicum.android.microhh.filters.domain.model.FilterSettings
import ru.practicum.android.microhh.search.domain.api.VacancySearchRepository

class VacancySearchUseCase(
    private val repository: VacancySearchRepository
) {

    operator fun invoke(term: String, page: Int = 0, filters: FilterSettings): Flow<VacancySearchState> {
        repository.buildQuery(term, page, filters)
        return repository.searchVacancy()
    }
}
