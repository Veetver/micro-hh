package ru.practicum.android.microhh.core.domain.interactors.favorites

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.domain.models.JobInfo
import ru.practicum.android.microhh.core.domain.models.VacancyListItem

interface FavoriteJobInteractor {
    fun findAll(): Flow<List<VacancyListItem>>
    fun findById(id: Long): Flow<VacancyListItem?>
    suspend fun add(job: JobInfo)
    suspend fun remove(job: JobInfo)
}
