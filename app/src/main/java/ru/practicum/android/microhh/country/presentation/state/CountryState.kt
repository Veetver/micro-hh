package ru.practicum.android.microhh.country.presentation.state

import ru.practicum.android.microhh.core.domain.models.Area

sealed interface CountryState {
    data object NoCountries : CountryState
    data object Loading : CountryState

    class ConnectionError(
        val error: Int,
    ) : CountryState

    class ShowCountries(
        var result: List<Area>,
    ) : CountryState
}
