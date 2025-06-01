package ru.practicum.android.microhh.core.presentation.ui.component.recycler

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.microhh.search.domain.model.VacancyListItem

class DiffCallback : DiffUtil.ItemCallback<VacancyListItem>() {

    override fun areItemsTheSame(oldItem: VacancyListItem, newItem: VacancyListItem): Boolean {
        return when {
            oldItem is VacancyListItem.VacancyItem && newItem is VacancyListItem.VacancyItem -> {
                oldItem.vacancy.id == newItem.vacancy.id
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: VacancyListItem, newItem: VacancyListItem): Boolean {
        return when {
            oldItem is VacancyListItem.VacancyItem && newItem is VacancyListItem.VacancyItem -> {
                oldItem.vacancy == newItem.vacancy
            }
            oldItem is VacancyListItem.Loading && newItem is VacancyListItem.Loading -> true
            else -> false
        }
    }
}
