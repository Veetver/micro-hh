package ru.practicum.android.microhh.favorites.presentation.ui.interfaces

import ru.practicum.android.microhh.core.domain.models.JobInfo

sealed class FavoriteJobScreenState {
    data object Initial : FavoriteJobScreenState()
    data object Loading : FavoriteJobScreenState()
    data class FavoriteContent(
        val jobs: List<JobInfo>
    ) : FavoriteJobScreenState()
    data object Empty : FavoriteJobScreenState()
    data class Error(val message: String? = null) : FavoriteJobScreenState()
}
