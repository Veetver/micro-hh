package ru.practicum.android.microhh.core.presentation.ui.component.recycler

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.practicum.android.microhh.core.domain.models.Vacancy
import ru.practicum.android.microhh.search.domain.model.VacancyListItem

class VacancyAdapter(
    onClick: (Vacancy) -> Unit = {},
) : AsyncListDifferDelegationAdapter<VacancyListItem>(DiffCallback()) {

    init {
        delegatesManager
            .addDelegate(trackItemDelegate(onClick))
            .addDelegate(loadingItemDelegate())
    }

    fun submitVacancyList(
        list: List<Vacancy>,
        isNextPage: Boolean = false,
    ) {
        val items = convertToVacancyListItem(list, isNextPage)
        differ.submitList(items)
    }

    private fun convertToVacancyListItem(
        list: List<Vacancy>,
        isNextPage: Boolean,
    ): List<VacancyListItem> {
        return buildList {
            this += list.map { VacancyListItem.VacancyItem(it) }

            if (isNextPage) {
                this += VacancyListItem.Loading
            }
        }
    }
}
