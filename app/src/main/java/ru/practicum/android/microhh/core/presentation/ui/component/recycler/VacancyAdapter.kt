package ru.practicum.android.microhh.core.presentation.ui.component.recycler

import androidx.recyclerview.widget.RecyclerView
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

    fun hideLoading() {
        val position = itemCount - 1
        if (position == RecyclerView.NO_POSITION) return

        val lastItem = differ.currentList[position]
        if (lastItem is VacancyListItem.Loading) {
            val items = differ.currentList - lastItem
            differ.submitList(items)
        }
    }

    fun submitVacancyList(
        list: List<Vacancy>,
        isNextPage: Boolean = false,
        doOnEnd: (() -> Unit) = {},
    ) {
        val items = convertToVacancyListItem(list, isNextPage)

        differ.submitList(items) {
            doOnEnd()
        }
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
