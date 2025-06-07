package ru.practicum.android.microhh.core.resources

import ru.practicum.android.microhh.core.domain.models.Catalog

sealed class CatalogSearchState {

    data object Default : CatalogSearchState()
    data object Loading : CatalogSearchState()
    class NoData(
        val error: Int,
    ): CatalogSearchState()
    class Results(
        val catalog: List<Catalog>,
    ) : CatalogSearchState()
}
