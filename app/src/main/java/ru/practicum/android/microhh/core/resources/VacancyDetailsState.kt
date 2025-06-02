package ru.practicum.android.microhh.core.resources

import ru.practicum.android.microhh.core.models.items.Vacancy


sealed class VacancyDetailsState(
    var vacancy: Vacancy? = null,
    val error: Int? = null,
    val term: String = "",
) {

    class Success(
        vacancy: Vacancy,
        term: String
    ) : VacancyDetailsState(vacancy, term = term)

    class Error(error: Int, term: String) : VacancyDetailsState(error = error, term = term)
}
