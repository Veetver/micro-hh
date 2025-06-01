package ru.practicum.android.microhh.search.domain.model

import ru.practicum.android.microhh.core.domain.models.Vacancy

sealed class VacancyListItem {
    data class VacancyItem(val vacancy: Vacancy) : VacancyListItem()
    data object Loading : VacancyListItem()
}
