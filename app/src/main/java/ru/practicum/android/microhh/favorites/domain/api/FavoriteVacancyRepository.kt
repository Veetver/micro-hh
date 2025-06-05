package ru.practicum.android.microhh.favorites.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.microhh.core.domain.models.Vacancy
import ru.practicum.android.microhh.core.domain.models.VacancyDetails

interface FavoriteVacancyRepository {
    fun findAll(): Flow<List<Vacancy>>
    fun findById(id: Long): Flow<Vacancy>
    suspend fun isVacancyFavorite(id: Long): Flow<Boolean>
    suspend fun add(vacancy: VacancyDetails)
    suspend fun remove(vacancy: VacancyDetails)
}
