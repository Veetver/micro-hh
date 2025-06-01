package ru.practicum.android.microhh.core.domain.interactors.favorites

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.domain.models.JobInfo
import ru.practicum.android.microhh.core.domain.repositories.favorites.FavoriteJobRepository

class FavoriteJobInteractorImpl(
    private val favoriteJobRepository: FavoriteJobRepository
) : FavoriteJobInteractor {
    override fun findAll(): Flow<List<JobInfo>> {
        return favoriteJobRepository.findAll()
    }

    override fun findById(id: Long): Flow<JobInfo?> {
        return favoriteJobRepository.findById(id)
    }

    override suspend fun add(job: JobInfo) {
        favoriteJobRepository.add(job)
    }

    override suspend fun remove(job: JobInfo) {
        favoriteJobRepository.remove(job)
    }

}
