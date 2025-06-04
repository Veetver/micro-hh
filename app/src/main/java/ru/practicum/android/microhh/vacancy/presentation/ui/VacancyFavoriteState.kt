package ru.practicum.android.microhh.vacancy.presentation.ui

sealed class VacancyFavoriteState(
    val isFavorite: Boolean?,
) {
    data object VacancyFavorite : VacancyFavoriteState(true)
    data object VacancyNotFavorite : VacancyFavoriteState(false)
    data object Loading : VacancyFavoriteState(null)
    data object Success : VacancyFavoriteState(null)
    data object Error : VacancyFavoriteState(null)
}

