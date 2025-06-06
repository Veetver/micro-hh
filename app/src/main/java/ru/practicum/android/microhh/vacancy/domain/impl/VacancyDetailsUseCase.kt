package ru.practicum.android.microhh.vacancy.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.core.resources.VacancyDetailsState
import ru.practicum.android.microhh.favorites.domain.api.FavoriteVacancyRepository
import ru.practicum.android.microhh.vacancy.domain.api.VacancyDetailsRepository

class VacancyDetailsUseCase(
    private val repository: VacancyDetailsRepository,
    private val favoriteRepository: FavoriteVacancyRepository
) {
    operator fun invoke(term: String): Flow<VacancyDetailsState> = flow {
        val id = term.toLongOrNull()
        if (id == null) {
            emit(VacancyDetailsState.Error(R.string.invalid_vacancy_id, term))
            return@flow
        }

        try {
            val state = repository.getVacancyDetails(term).first()

            when (state) {
                is VacancyDetailsState.Success -> emit(state)
                is VacancyDetailsState.Error -> checkAndEmitCachedIfFavorite(id, term)
            }
        } catch (e: Exception) {
            checkAndEmitCachedIfFavorite(id, term)
        }
    }

    private suspend fun FlowCollector<VacancyDetailsState>.checkAndEmitCachedIfFavorite(id: Long, term: String) {
        val isFavorite = favoriteRepository.isVacancyFavorite(id).first()

        if (isFavorite) {
            val item = favoriteRepository.findById(id).first()
            item?.let { VacancyDetailsState.Success(it, term) }?.let { emit(it) }
        } else {
            emit(VacancyDetailsState.Error(R.string.no_internet_and_not_favorite, term))
        }
    }
}
