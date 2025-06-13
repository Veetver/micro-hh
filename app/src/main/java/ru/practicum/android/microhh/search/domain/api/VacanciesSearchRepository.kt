package ru.practicum.android.microhh.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.resources.VacancySearchState
import ru.practicum.android.microhh.filters.domain.model.FilterSettings

interface VacanciesSearchRepository {
    fun searchVacancies(term: String, page: Int, filters: FilterSettings): Flow<VacancySearchState>
}
