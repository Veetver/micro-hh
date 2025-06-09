package ru.practicum.android.microhh.core.presentation.ui.component.recycler

import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.practicum.android.microhh.core.domain.models.Catalog
import ru.practicum.android.microhh.core.resources.CatalogListItem
import ru.practicum.android.microhh.core.resources.CatalogListItemType

class CatalogAdapter(
    onClick: (Catalog) -> Unit,
) : AsyncListDifferDelegationAdapter<CatalogListItem>(CatalogDiffCallback()) {

    private var selectedPosition = RecyclerView.NO_POSITION

    init {
        delegatesManager
            .addDelegate(
                checkboxItemDelegate(
                    onClick,
                    getSelectedPosition = { selectedPosition },
                    onItemSelected = { newPosition ->
                        val oldPosition = selectedPosition

                        selectedPosition = newPosition
                        if (oldPosition != RecyclerView.NO_POSITION) {
                            notifyItemChanged(oldPosition)
                        }
                        notifyItemChanged(newPosition)
                    }
                )
            )
        delegatesManager.addDelegate(
            arrowItemDelegate(
                onClick
            )
        )
    }

    fun submitCatalogList(
        list: List<Catalog>,
        type: String,
        doOnEnd: (() -> Unit) = {},
    ) {
        val items = convertToCatalogListItem(list, type)

        differ.submitList(items) {
            doOnEnd()
        }
    }

    private fun convertToCatalogListItem(
        list: List<Catalog>,
        type: String,
    ): List<CatalogListItem> {
        return buildList {
            when (type) {
                CatalogListItemType.CHECK_BOX_ITEM.name -> this += list.map { CatalogListItem.CheckboxItem(it) }
                CatalogListItemType.ARROW_ITEM.name -> this += list.map { CatalogListItem.ArrowItem(it) }
            }
        }
    }
}
