package ru.practicum.android.microhh.search.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.microhh.core.data.dto.VacancyDtoConverter
import ru.practicum.android.microhh.core.data.network.RetrofitNetworkClient
import ru.practicum.android.microhh.core.resources.QueryParams
import ru.practicum.android.microhh.core.resources.VacancySearchState
import ru.practicum.android.microhh.core.utils.Constants
import ru.practicum.android.microhh.filters.domain.model.FilterSettings
import ru.practicum.android.microhh.search.data.dto.RetrofitSearchRequest
import ru.practicum.android.microhh.search.data.dto.VacancyResponse
import ru.practicum.android.microhh.search.domain.api.VacancySearchRepository

class VacancySearchRepositoryImpl(
    private val networkClient: RetrofitNetworkClient,
    private val dtoConverter: VacancyDtoConverter,
) : VacancySearchRepository {

    private fun buildQuery(term: String, page: Int, filters: FilterSettings): HashMap<String, String> {
        val options: HashMap<String, String> = HashMap()

        options[QueryParams.TEXT.query] = term
        options[QueryParams.PAGE.query] = page.toString()
        options[QueryParams.PER_PAGE.query] = "20"

        if (filters.workplace.isNotEmpty()) {
            options[QueryParams.AREA.query] = "true"
        }

        if (filters.industry.isNotEmpty()) {
            options[QueryParams.INDUSTRY.query] = filters.industry
        }

        if (filters.salary.isNotEmpty()) {
            options[QueryParams.SALARY.query] = filters.salary
        }

        options[QueryParams.ONLY_WITH_SALARY.query] = filters.showWithoutSalary.toString()

        return options
    }

    override fun searchVacancy(term: String, page: Int, filters: FilterSettings): Flow<VacancySearchState> = flow {
        val options = buildQuery(term, page, filters)
        val response = networkClient.doRequest(RetrofitSearchRequest(options))

        when (response.resultCode) {
            Constants.HTTP_OK -> {
                val result = response as VacancyResponse

                if (result.items.isEmpty()) {
                    emit(VacancySearchState.Success(
                        emptyList(),
                        0,
                        0,
                        options[QueryParams.TEXT.query])
                    )
                } else {
                    val vacancies = dtoConverter.toVacancyList(result.items)
                    emit(VacancySearchState.Success(
                        vacancies,
                        result.pages,
                        result.found,
                        options[QueryParams.TEXT.query])
                    )
                }
            }
            else -> {
                emit(VacancySearchState.Error(Constants.NO_CONNECTION, options[QueryParams.TEXT.query]))
            }
        }
    }
}
