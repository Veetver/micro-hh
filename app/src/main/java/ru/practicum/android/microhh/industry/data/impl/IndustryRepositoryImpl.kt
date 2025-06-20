package ru.practicum.android.microhh.industry.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.microhh.core.resources.CatalogState
import ru.practicum.android.microhh.core.utils.Constants
import ru.practicum.android.microhh.core.utils.DtoConverter.toCatalogList
import ru.practicum.android.microhh.industry.data.dto.IndustryResponse
import ru.practicum.android.microhh.industry.data.network.IndustryNetworkClient
import ru.practicum.android.microhh.industry.domain.api.IndustryRepository

class IndustryRepositoryImpl(
    private val networkClient: IndustryNetworkClient,
) : IndustryRepository {

    override fun getIndustries(): Flow<CatalogState> = flow {
        val response = networkClient.getIndustries()

        when (response.resultCode) {
            Constants.HTTP_OK -> {
                when (response) {
                    is IndustryResponse -> {
                        emit(CatalogState.Success(response.items.toCatalogList()))
                    }
                }
            }

            Constants.INTERNAL_SERVER_ERROR -> {
                emit(CatalogState.Error(Constants.INTERNAL_SERVER_ERROR))
            }

            else -> {
                emit(CatalogState.Error(Constants.NO_CONNECTION))
            }
        }
    }

}
