package ru.practicum.android.microhh.core.presentation.ui.component.recycler

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.microhh.core.domain.models.VacancyListItem

class DiffCallback : DiffUtil.ItemCallback<VacancyListItem>() {

    override fun areItemsTheSame(oldItem: VacancyListItem, newItem: VacancyListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: VacancyListItem, newItem: VacancyListItem): Boolean {
        return oldItem == newItem
    }
}
