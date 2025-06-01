package ru.practicum.android.microhh.core.domain.interactors.favorites

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.microhh.core.domain.models.JobInfo
import ru.practicum.android.microhh.core.domain.models.VacancyListItem
import ru.practicum.android.microhh.core.domain.repositories.favorites.FavoriteJobRepository

class FavoriteJobInteractorImpl(
    private val favoriteJobRepository: FavoriteJobRepository
) : FavoriteJobInteractor {
    override fun findAll(): Flow<List<VacancyListItem>> {
        return favoriteJobRepository.findAll()
            .map { jobList ->
                jobList.map { jobVacancy ->
                    convertJobInfo(jobVacancy)
                }
            }
    }

    override fun findById(id: Long): Flow<VacancyListItem?> {
        return favoriteJobRepository.findById(id).map { jobVacancy ->
            convertFromJobInfo(jobVacancy)
        }
    }

    override suspend fun add(job: JobInfo) {
        favoriteJobRepository.add(job)
    }

    override suspend fun remove(job: JobInfo) {
        favoriteJobRepository.remove(job)
    }

    private fun convertFromJobInfo(item: JobInfo?): VacancyListItem? {
        if (item == null) {
            return null
        }
        return convertJobInfo(item)
    }

    private fun convertJobInfo(item: JobInfo): VacancyListItem {
        return VacancyListItem(
            id = item.id,
            companyName = item.employerName,
            title = item.name,
            salaryDisplayText = "${item.salaryFrom} ${item.salaryTo}",
            companyLogo = ""
        )
    }

}
