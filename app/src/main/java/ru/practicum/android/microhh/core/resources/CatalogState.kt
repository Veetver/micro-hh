package ru.practicum.android.microhh.core.resources

import ru.practicum.android.microhh.core.domain.models.Catalog

sealed class CatalogState(
    val catalog: List<Catalog> = emptyList(),
    val error: Int? = null,
) {

    class Success(catalog: List<Catalog>) : CatalogState(catalog)
    class Error(error: Int) : CatalogState(error = error)
}
