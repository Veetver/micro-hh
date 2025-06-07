package ru.practicum.android.microhh.core.presentation.ui.component

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.microhh.core.domain.models.Country
import ru.practicum.android.microhh.databinding.ItemCountryBinding

class CountryViewHolder(
    private val binding: ItemCountryBinding,
    private val onItemClick: (Country) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Country) {
        binding.countryName.text = item.name
        binding.root.setOnClickListener {
            onItemClick(item)
        }
    }
}
