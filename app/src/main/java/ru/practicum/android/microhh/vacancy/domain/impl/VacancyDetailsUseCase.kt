package ru.practicum.android.microhh.vacancy.domain.impl

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.core.domain.models.VacancyDetails
import ru.practicum.android.microhh.core.resources.VacancyDetailsState
import ru.practicum.android.microhh.core.utils.AppLog
import ru.practicum.android.microhh.favorites.domain.api.FavoriteVacancyRepository
import ru.practicum.android.microhh.vacancy.domain.api.VacancyDetailsRepository
import java.io.IOException

class VacancyDetailsUseCase(
    private val repository: VacancyDetailsRepository,
    private val favoriteRepository: FavoriteVacancyRepository
) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    operator fun invoke(term: String): Flow<VacancyDetailsState> = flow {
        val id = term.toLongOrNull()
        if (id == null) {
            emit(VacancyDetailsState.Error(R.string.invalid_vacancy_id, term))
        } else {
            try {
                val state = repository.getVacancyDetails(term).first()

                when (state) {
                    is VacancyDetailsState.Success -> {
                        checkAndMaybeUpdateFavorite(state.vacancy!!, id)
                        emit(state)
                    }
                    is VacancyDetailsState.Error -> checkAndEmitCachedIfFavorite(id, term)
                }
            } catch (e: IOException) {
                AppLog.d(AppLog.RETROFIT_API_RESPONSE, AppLog.getStackTraceString(e))
                checkAndEmitCachedIfFavorite(id, term)
            } catch (e: HttpException) {
                AppLog.d(AppLog.RETROFIT_API_RESPONSE, AppLog.getStackTraceString(e))
                checkAndEmitCachedIfFavorite(id, term)
            }
        }
    }

    private suspend fun checkAndMaybeUpdateFavorite(vacancy: VacancyDetails, id: Long) {
        val isFavorite = favoriteRepository.isVacancyFavorite(id).first()
        if (isFavorite) {
            favoriteRepository.update(vacancy)
        }
    }

    private suspend fun FlowCollector<VacancyDetailsState>.checkAndEmitCachedIfFavorite(id: Long, term: String) {
        val isFavorite = favoriteRepository.isVacancyFavorite(id).first()

        if (isFavorite) {
            val favoriteVacancy = favoriteRepository.findById(id).firstOrNull()
            if (favoriteVacancy != null) {
                emit(VacancyDetailsState.Success(favoriteVacancy, term))
            } else {
                emit(VacancyDetailsState.Error(R.string.no_internet, term))
            }
        } else {
            emit(VacancyDetailsState.Error(R.string.no_internet_and_not_favorite, term))
        }
    }
}
