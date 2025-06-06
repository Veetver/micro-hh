package ru.practicum.android.microhh.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.resources.VacancySearchState
import ru.practicum.android.microhh.filters.domain.model.FilterSettings

interface VacancySearchRepository {
    fun buildQuery(term: String, page: Int, filters: FilterSettings)
    fun searchVacancy(): Flow<VacancySearchState>
}
