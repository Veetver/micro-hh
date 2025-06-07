package ru.practicum.android.microhh.search.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.microhh.core.data.dto.VacancyDtoConverter
import ru.practicum.android.microhh.core.data.network.RetrofitNetworkClient
import ru.practicum.android.microhh.core.resources.VacancySearchState
import ru.practicum.android.microhh.core.utils.Constants
import ru.practicum.android.microhh.search.data.dto.RetrofitSearchRequest
import ru.practicum.android.microhh.search.data.dto.VacancyResponse
import ru.practicum.android.microhh.search.domain.api.VacancySearchRepository

class VacancySearchRepositoryImpl(
    private val networkClient: RetrofitNetworkClient,
    private val dtoConverter: VacancyDtoConverter,
) : VacancySearchRepository {

    override fun searchVacancy(term: String, page: Int): Flow<VacancySearchState> = flow {
        val response = networkClient.doRequest(RetrofitSearchRequest(term, page))

        when (response.resultCode) {
            Constants.HTTP_OK -> {
                val result = response as VacancyResponse

                if (result.items.isEmpty()) {
                    emit(VacancySearchState.Success(emptyList(), 0, 0, term))
                } else {
                    val vacancies = dtoConverter.toVacancyList(result.items)
                    emit(VacancySearchState.Success(vacancies, result.pages, result.found, term))
                }
            }
            else -> {
                emit(VacancySearchState.Error(Constants.NO_CONNECTION, term))
            }
        }
    }
}
