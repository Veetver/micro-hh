package ru.practicum.android.microhh.vacancy.presentation.ui

import ru.practicum.android.microhh.vacancy.data.VacancyDetailsDto

sealed class VacancyState(
    val term: String?,
) {
    data object VacancyNotExist : VacancyState(null)
    data object Loading : VacancyState(null)

    class ConnectionError(
        val error: Int,
        term: String,
    ) : VacancyState(term)

    class ShowDetails(
        var result: VacancyDetailsDto,
        term: String?,
    ) : VacancyState(term)
}

