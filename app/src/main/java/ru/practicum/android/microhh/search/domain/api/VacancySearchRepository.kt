package ru.practicum.android.microhh.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.resources.VacancySearchState
import ru.practicum.android.microhh.filters.domain.model.FilterSettings

interface VacancySearchRepository {
    fun searchVacancy(term: String, page: Int, filters: FilterSettings): Flow<VacancySearchState>
}
