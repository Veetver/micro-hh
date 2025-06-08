package ru.practicum.android.microhh.core.resources

import ru.practicum.android.microhh.core.domain.models.Vacancy

sealed class FavoriteVacancyScreenState {
    data object Initial : FavoriteVacancyScreenState()
    data object Loading : FavoriteVacancyScreenState()
    data class FavoriteContent(
        val results: List<Vacancy>
    ) : FavoriteVacancyScreenState()
    data object Empty : FavoriteVacancyScreenState()
    data class Error(val message: String? = null) : FavoriteVacancyScreenState()
}
