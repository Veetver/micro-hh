package ru.practicum.android.microhh.core.domain.interactors.favorites

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.domain.models.JobInfo

interface FavoriteJobInteractor {
    fun findAll(): Flow<List<JobInfo>>
    fun findById(id: Long): Flow<JobInfo?>
    suspend fun add(job: JobInfo)
    suspend fun remove(job: JobInfo)
}
