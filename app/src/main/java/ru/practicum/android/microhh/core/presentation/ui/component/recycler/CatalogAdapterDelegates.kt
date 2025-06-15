package ru.practicum.android.microhh.core.presentation.ui.component.recycler

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.practicum.android.microhh.core.domain.models.Catalog
import ru.practicum.android.microhh.core.resources.CatalogListItem
import ru.practicum.android.microhh.databinding.ItemArrowBinding
import ru.practicum.android.microhh.databinding.ItemCheckboxBinding

fun checkboxItemDelegate(
    onClick: (Catalog) -> Unit,
    getSelectedPosition: () -> Int?,
    onItemSelected: (Int) -> Unit,
) = adapterDelegateViewBinding<CatalogListItem.CheckboxItem, CatalogListItem, ItemCheckboxBinding>(
    { layoutInflater, root -> ItemCheckboxBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        val catalog = item.catalog

        with(binding.catalogName) {
            text = catalog.name
            isChecked = bindingAdapterPosition == getSelectedPosition()
            isClickable = bindingAdapterPosition != getSelectedPosition()
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked && bindingAdapterPosition != getSelectedPosition()) {
                    onClick(catalog)
                    onItemSelected(bindingAdapterPosition)
                }
            }
        }
    }
}

fun arrowItemDelegate(
    onClick: (Catalog) -> Unit,
) = adapterDelegateViewBinding<CatalogListItem.ArrowItem, CatalogListItem, ItemArrowBinding>(
    { layoutInflater, root -> ItemArrowBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        val catalog = item.catalog

        binding.catalogName.text = catalog.name
        itemView.setOnClickListener { onClick(catalog) }
    }
}
