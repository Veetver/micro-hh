package ru.practicum.android.microhh.vacancy.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.microhh.core.data.dto.VacancyDtoConverter
import ru.practicum.android.microhh.core.data.network.RetrofitNetworkClient
import ru.practicum.android.microhh.core.resources.VacancyDetailsState
import ru.practicum.android.microhh.core.utils.Constants
import ru.practicum.android.microhh.vacancy.data.dto.RetrofitVacancyDetailsRequest
import ru.practicum.android.microhh.vacancy.data.dto.VacancyDetailsResponse
import ru.practicum.android.microhh.vacancy.domain.api.VacancyDetailsRepository

class VacancyDetailsRepositoryImpl(
    private val networkClient: RetrofitNetworkClient,
    private val dtoConverter: VacancyDtoConverter,
) : VacancyDetailsRepository {

    override fun getVacancyDetails(term: String): Flow<VacancyDetailsState> = flow {
        val response = networkClient.getVacancy(RetrofitVacancyDetailsRequest(term))

        when (response.resultCode) {
            Constants.HTTP_OK -> {
                when (response) {
                    is VacancyDetailsResponse -> {
                        val vacancy = dtoConverter.toVacancyDetails(response)
                        emit(VacancyDetailsState.Success(vacancy, term))
                    }
                }
            }
            else -> {
                emit(VacancyDetailsState.Error(Constants.NO_CONNECTION, term))
            }
        }
    }
}
