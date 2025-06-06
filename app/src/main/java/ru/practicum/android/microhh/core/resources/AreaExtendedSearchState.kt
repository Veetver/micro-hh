package ru.practicum.android.microhh.core.resources

import ru.practicum.android.microhh.core.domain.models.AreaExtended

sealed class AreaExtendedSearchState(
    var area: AreaExtended? = null,
    val error: Int? = null,
) {
    class Success(
        area: AreaExtended,
    ) : AreaExtendedSearchState(area = area)

    class Error(error: Int) : AreaExtendedSearchState(error = error)
}
