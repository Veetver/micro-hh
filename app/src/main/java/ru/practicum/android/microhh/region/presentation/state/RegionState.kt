package ru.practicum.android.microhh.region.presentation.state

import ru.practicum.android.microhh.core.domain.models.Catalog
import ru.practicum.android.microhh.country.presentation.state.CountryState

sealed interface RegionState {
    data object NoRegions : RegionState
    data object Loading : RegionState

    class ConnectionError(
        val error: Int,
    ) : RegionState

    class ShowCountries(
        var result: List<Catalog>,
    ) : RegionState
}
