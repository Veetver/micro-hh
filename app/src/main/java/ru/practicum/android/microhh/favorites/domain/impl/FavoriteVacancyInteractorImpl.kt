package ru.practicum.android.microhh.favorites.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.domain.models.Vacancy
import ru.practicum.android.microhh.core.domain.models.VacancyDetails
import ru.practicum.android.microhh.favorites.domain.api.FavoriteVacancyInteractor
import ru.practicum.android.microhh.favorites.domain.api.FavoriteVacancyRepository

class FavoriteVacancyInteractorImpl(
    private val favoriteJobRepository: FavoriteVacancyRepository
) : FavoriteVacancyInteractor {

    override fun findAll(): Flow<List<Vacancy>> {
        return favoriteJobRepository.findAll()
    }

    override fun findById(id: Long): Flow<Vacancy> {
        return favoriteJobRepository.findById(id)
    }

    override suspend fun isVacancyFavorite(id: Long): Flow<Boolean> {
        return favoriteJobRepository.isVacancyFavorite(id)
    }

    override suspend fun add(vacancy: VacancyDetails) {
        favoriteJobRepository.add(vacancy)
    }

    override suspend fun remove(vacancy: VacancyDetails) {
        favoriteJobRepository.remove(vacancy)
    }

}
