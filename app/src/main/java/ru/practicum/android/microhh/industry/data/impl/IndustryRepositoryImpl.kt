package ru.practicum.android.microhh.industry.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.microhh.core.data.dto.IndustryDtoConverter
import ru.practicum.android.microhh.core.data.network.RetrofitNetworkClient
import ru.practicum.android.microhh.core.resources.IndustryState
import ru.practicum.android.microhh.core.utils.Constants
import ru.practicum.android.microhh.industry.data.dto.IndustryItemResponse
import ru.practicum.android.microhh.industry.domain.api.IndustryRepository

class IndustryRepositoryImpl(
    private val networkClient: RetrofitNetworkClient,
    private val dtoConverter: IndustryDtoConverter,
) : IndustryRepository {

    override fun getIndustries(): Flow<IndustryState> = flow {
        val response = networkClient.getIndustries()

        when (response.resultCode) {
            Constants.HTTP_OK -> {
                when (response) {
                    is IndustryItemResponse -> {
                        val industries = dtoConverter.toIndustryList(response.items)
                        emit(IndustryState.Success(industries))
                    }
                }
            }
            else -> {
                emit(IndustryState.Error(Constants.NO_CONNECTION))
            }
        }
    }

}
