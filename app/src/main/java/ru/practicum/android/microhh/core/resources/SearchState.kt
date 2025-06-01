package ru.practicum.android.microhh.core.resources

import ru.practicum.android.microhh.search.data.dto.VacancyDto

sealed class SearchState(
    val term: String?,
) {

    data object NoData: SearchState(null)
    data object Loading: SearchState(null)
    class NothingFound(
        term: String,
    ): SearchState(term)
    class ConnectionError(
        val error: Int,
        term: String,
    ) : SearchState(term)
    class SearchResults(
        var results: List<VacancyDto>,
        val count: Int,
        term: String?,
    ) : SearchState(term)
}
