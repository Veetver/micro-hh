package ru.practicum.android.microhh.favorites.presentation.ui.interfaces

import ru.practicum.android.microhh.core.domain.models.JobInfo

sealed class FavoriteJobScreenState {
    data class FavoriteContent(
        val jobs: List<JobInfo>?,
    ) : FavoriteJobScreenState()

}
