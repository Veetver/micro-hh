package ru.practicum.android.microhh.core.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.microhh.core.db.AppDataBase
import ru.practicum.android.microhh.core.db.JobInfoDBConvertor
import ru.practicum.android.microhh.core.db.JobInfoEntity
import ru.practicum.android.microhh.core.domain.models.JobInfo
import ru.practicum.android.microhh.core.domain.repositories.favorites.FavoriteJobRepository

class FavoriteJobRepositoryImpl(
    private val appDatabase: AppDataBase,
    private val jobInfoDBConvertor: JobInfoDBConvertor,
) : FavoriteJobRepository {

    override fun findAll(): Flow<List<JobInfo>> {
        return flow {
            val jobs = appDatabase.favoriteJobDao().getJobs()
            emit(convertFromJobEntity(jobs))
        }
    }

    override fun findById(id: Long): Flow<JobInfo?> {
        return flow {
            val job = appDatabase.favoriteJobDao().getJobById(id)
            emit(jobInfoDBConvertor.map(job))
        }
    }

    override suspend fun add(job: JobInfo) {
        appDatabase.favoriteJobDao().insertJob(
            jobInfoDBConvertor.map(job)
        )
    }

    override suspend fun remove(job: JobInfo) {
        appDatabase.favoriteJobDao().deleteJob(
            job.id
        )
    }

    private fun convertFromJobEntity(items: List<JobInfoEntity>): List<JobInfo> {
        return items.map { job -> jobInfoDBConvertor.map(job)!! }
    }
}
