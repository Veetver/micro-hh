package ru.practicum.android.microhh.favorites.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.microhh.core.db.AppDataBase
import ru.practicum.android.microhh.core.db.api.VacancyDetailsDBConvertor
import ru.practicum.android.microhh.core.domain.models.Vacancy
import ru.practicum.android.microhh.core.domain.models.VacancyDetails
import ru.practicum.android.microhh.favorites.domain.api.FavoriteVacancyRepository

class FavoriteVacancyRepositoryImpl(
    private val appDatabase: AppDataBase,
    private val dtoConverter: VacancyDetailsDBConvertor,
) : FavoriteVacancyRepository {

    override fun findAll(): Flow<List<Vacancy>> {
        return flow {
            val vacancies = appDatabase.favoriteVacanciesDao().getVacancies()
            val list = vacancies.map {
                dtoConverter.toVacancy(it)
            }
            emit(list)
        }
    }

    override fun findById(id: Long): Flow<VacancyDetails?> {
        return flow {
            val vacancy = appDatabase.favoriteVacanciesDao().getVacancyById(id)
            vacancy?.let { emit(dtoConverter.toVacancyDetails(it)) }
        }
    }

    override suspend fun isVacancyFavorite(id: Long): Flow<Boolean> = flow {
        val isFavorite = appDatabase.favoriteVacanciesDao().isVacancyFavorite(id)
        emit(isFavorite)
    }

    override suspend fun add(vacancy: VacancyDetails) {
        appDatabase.favoriteVacanciesDao().insertVacancy(
            dtoConverter.toVacancyDetailsEntity(vacancy)
        )
    }

    override suspend fun remove(vacancy: VacancyDetails) {
        appDatabase.favoriteVacanciesDao().deleteVacancy(
            vacancy.id
        )
    }
}
