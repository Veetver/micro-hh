package ru.practicum.android.microhh.core.resources

import ru.practicum.android.microhh.core.domain.models.Country

sealed class CountrySearchState(
    var countries: List<Country> = emptyList(),
    val error: Int? = null,
    val term: String = "",
) {

    class Success(
        countries: List<Country>,
        term: String
    ) : CountrySearchState(countries = countries, term = term)

    class Error(error: Int, term: String) : CountrySearchState(error = error, term = term)
}
