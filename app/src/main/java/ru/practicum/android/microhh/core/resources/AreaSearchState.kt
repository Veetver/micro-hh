package ru.practicum.android.microhh.core.resources

import ru.practicum.android.microhh.core.domain.models.Area

sealed class AreaSearchState(
    var areas: List<Area> = emptyList(),
    val error: Int? = null,
) {
    class Success(
        areas: List<Area>,
    ) : AreaSearchState(areas = areas)

    class Error(error: Int) : AreaSearchState(error = error)
}
