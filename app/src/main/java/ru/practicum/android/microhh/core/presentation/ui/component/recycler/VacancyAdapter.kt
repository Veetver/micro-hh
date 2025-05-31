package ru.practicum.android.microhh.core.presentation.ui.component.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.microhh.core.domain.models.VacancyListItem
import ru.practicum.android.microhh.databinding.ItemVacancyBinding

class VacancyAdapter(
    private val onClick: (VacancyListItem) -> Unit = {},
) : ListAdapter<VacancyListItem, RecyclerView.ViewHolder>(DiffCallback()) {

    fun submitVacancyList(
        list: List<VacancyListItem>,
    ) {
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVacancyBinding.inflate(inflater, parent, false)
        return VacancyViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as VacancyViewHolder).bind(item)
    }
}
