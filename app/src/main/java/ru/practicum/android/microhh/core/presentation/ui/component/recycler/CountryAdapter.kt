package ru.practicum.android.microhh.core.presentation.ui.component.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.microhh.core.domain.models.Area
import ru.practicum.android.microhh.core.presentation.ui.component.CountryViewHolder
import ru.practicum.android.microhh.databinding.ItemCountryBinding

class CountryAdapter(private val onItemClick: (Area) -> Unit) :
    RecyclerView.Adapter<CountryViewHolder>() {

    var countriesList = mutableListOf<Area>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCountryBinding.inflate(layoutInflater, parent, false)
        return CountryViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countriesList[position])
    }

    override fun getItemCount() = countriesList.size
}
