package ru.practicum.android.microhh.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.resources.VacancySearchState

interface VacancySearchRepository {
    fun searchVacancy(term: String, page: Int): Flow<VacancySearchState>
}
