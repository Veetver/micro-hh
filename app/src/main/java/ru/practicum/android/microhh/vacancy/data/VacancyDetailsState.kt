package ru.practicum.android.microhh.vacancy.data

sealed class VacancyDetailsState(
    var vacancy: VacancyDetailsDto? = null,
    val error: Int? = null,
    val term: String = "",
) {

    class Success(
        vacancy: VacancyDetailsDto,
        term: String
    ) : VacancyDetailsState(vacancy, term = term)

    class Error(error: Int, term: String) : VacancyDetailsState(error = error, term = term)
}
