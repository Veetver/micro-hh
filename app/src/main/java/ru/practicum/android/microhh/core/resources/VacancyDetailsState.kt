package ru.practicum.android.microhh.core.resources

import ru.practicum.android.microhh.core.domain.models.VacancyDetails

sealed class VacancyDetailsState(
    var vacancy: VacancyDetails? = null,
    val error: Int? = null,
    val term: String = "",
) {

    class Success(
        vacancy: VacancyDetails,
        term: String
    ) : VacancyDetailsState(vacancy, term = term)

    class Error(error: Int, term: String) : VacancyDetailsState(error = error, term = term)
}
