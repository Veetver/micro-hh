package ru.practicum.android.microhh.core.presentation.ui.component

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.microhh.core.domain.models.Catalog
import ru.practicum.android.microhh.databinding.ItemCountryBinding

class CountryViewHolder(
    private val binding: ItemCountryBinding,
    private val onItemClick: (Catalog) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Catalog) {
        binding.countryName.text = item.name
        binding.root.setOnClickListener {
            onItemClick(item)
        }
    }
}
