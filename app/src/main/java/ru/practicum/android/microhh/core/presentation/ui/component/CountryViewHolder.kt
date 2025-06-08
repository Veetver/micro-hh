package ru.practicum.android.microhh.core.presentation.ui.component

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.microhh.core.domain.models.Area
import ru.practicum.android.microhh.databinding.ItemCountryBinding

class CountryViewHolder(
    private val binding: ItemCountryBinding,
    private val onItemClick: (Area) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Area) {
        binding.countryName.text = item.name
        binding.root.setOnClickListener {
            onItemClick(item)
        }
    }
}
