package ru.practicum.android.microhh.core.resources

import ru.practicum.android.microhh.core.domain.models.Catalog

sealed class CatalogListItem {
    data class CheckboxItem(val catalog: Catalog) : CatalogListItem()
    data class ArrowItem(val catalog: Catalog) : CatalogListItem()
}
