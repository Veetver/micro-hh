package ru.practicum.android.microhh.core.resources

import ru.practicum.android.microhh.search.data.dto.VacancyDto

sealed class VacancySearchState(
    var vacancies: List<VacancyDto> = emptyList(),
    val error: Int? = null,
    val pagesCount: Int = 0,
    val vacanciesCount: Int = 0,
    val term: String = "",
) {

    class Success(
        vacancies: List<VacancyDto>,
        pagesCount: Int,
        vacanciesCount: Int,
        term: String
    ) : VacancySearchState(
        vacancies,
        pagesCount = pagesCount,
        vacanciesCount = vacanciesCount,
        term = term
    )
    class Error(error: Int, term: String) : VacancySearchState(error = error, term = term)
}
