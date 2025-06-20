package ru.practicum.android.microhh.vacancy.presentation.ui

import ru.practicum.android.microhh.core.domain.models.VacancyDetails

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
        var result: VacancyDetails,
        term: String?,
    ) : VacancyState(term)
}

