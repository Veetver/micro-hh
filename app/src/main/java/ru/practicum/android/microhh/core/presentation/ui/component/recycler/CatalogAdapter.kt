package ru.practicum.android.microhh.core.presentation.ui.component.recycler

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.practicum.android.microhh.core.domain.models.Catalog
import ru.practicum.android.microhh.core.resources.CatalogListItem

class CatalogAdapter(
    onClick: (Catalog) -> Unit = {},
) : AsyncListDifferDelegationAdapter<CatalogListItem>(CatalogDiffCallback()) {

    init {
        delegatesManager
            .addDelegate(checkboxItemDelegate(onClick))
            .addDelegate(arrowItemDelegate(onClick))
    }

    fun submitCatalogList(
        list: List<Catalog>,
        type: CatalogListItem,
        doOnEnd: (() -> Unit) = {},
    ) {
        val items = convertToCatalogListItem(list, type)

        differ.submitList(items) {
            doOnEnd()
        }
    }

    private fun convertToCatalogListItem(
        list: List<Catalog>,
        type: CatalogListItem,
    ): List<CatalogListItem> {
        return buildList {
            when(type) {
                is CatalogListItem.CheckboxItem -> this += list.map { CatalogListItem.CheckboxItem(it) }
                is CatalogListItem.ArrowItem -> this += list.map { CatalogListItem.ArrowItem(it) }
            }
        }
    }
}
