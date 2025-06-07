package ru.practicum.android.microhh.core.presentation.ui.component.recycler

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.microhh.core.resources.CatalogListItem

class CatalogDiffCallback : DiffUtil.ItemCallback<CatalogListItem>() {

    override fun areItemsTheSame(oldItem: CatalogListItem, newItem: CatalogListItem): Boolean {
        return when {
            oldItem is CatalogListItem.CheckboxItem && newItem is CatalogListItem.CheckboxItem -> {
                oldItem.catalog.id == newItem.catalog.id
            }
            oldItem is CatalogListItem.ArrowItem && newItem is CatalogListItem.ArrowItem -> {
                oldItem.catalog.id == newItem.catalog.id
            }

            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: CatalogListItem, newItem: CatalogListItem): Boolean {
        return when {
            oldItem is CatalogListItem.CheckboxItem && newItem is CatalogListItem.CheckboxItem -> {
                oldItem.catalog == newItem.catalog
            }
            oldItem is CatalogListItem.ArrowItem && newItem is CatalogListItem.ArrowItem -> {
                oldItem.catalog == newItem.catalog
            }
            else -> false
        }
    }
}
