package ru.practicum.android.microhh.core.resources

import ru.practicum.android.microhh.core.domain.models.Vacancy

sealed class SearchState(
    val term: String?,
) {

    data object NoData: SearchState(null)
    data object Loading: SearchState(null)
    class NothingFound(
        term: String?,
    ): SearchState(term)
    class ConnectionError(
        val error: Int,
        val isNextPage: Boolean = false,
        term: String?,
    ) : SearchState(term)
    class SearchResults(
        var results: List<Vacancy>,
        val vacanciesCount: Int,
        term: String?,
        val canLoadMore: Boolean,
    ) : SearchState(term)
    class NextPage(
        var results: List<Vacancy>,
        val vacanciesCount: Int,
        val canLoadMore: Boolean,
    ) : SearchState(null)
}
