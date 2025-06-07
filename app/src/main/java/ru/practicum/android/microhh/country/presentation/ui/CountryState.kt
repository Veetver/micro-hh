package ru.practicum.android.microhh.country.presentation.ui

import ru.practicum.android.microhh.core.domain.models.Country

sealed class CountryState(
    val term: String?,
) {
    data object NoCountries : CountryState(null)
    data object Loading : CountryState(null)

    class ConnectionError(
        val error: Int,
        term: String,
    ) : CountryState(term)

    class ShowCountries(
        var result: List<Country>,
        term: String?,
    ) : CountryState(term)
}
