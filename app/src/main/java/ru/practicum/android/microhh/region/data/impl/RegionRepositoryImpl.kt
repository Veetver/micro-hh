package ru.practicum.android.microhh.region.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.microhh.core.data.network.RetrofitNetworkClient
import ru.practicum.android.microhh.core.resources.AreaExtendedSearchState
import ru.practicum.android.microhh.core.resources.AreaSearchState
import ru.practicum.android.microhh.core.utils.Constants
import ru.practicum.android.microhh.region.data.dto.request.AreaByIdRequest
import ru.practicum.android.microhh.region.data.dto.response.AreaExtendedResponse
import ru.practicum.android.microhh.region.data.dto.response.AreasResponse
import ru.practicum.android.microhh.region.data.mapper.toAreaExtended
import ru.practicum.android.microhh.region.data.mapper.toFlatAreas
import ru.practicum.android.microhh.region.domain.api.RegionRepository

class RegionRepositoryImpl(
    private val networkClient: RetrofitNetworkClient,
) : RegionRepository {
    override fun getRegions(): Flow<AreaSearchState> = flow {
        val response = networkClient.getAreas()

        when (response.resultCode) {
            Constants.HTTP_OK -> {
                when (response) {
                    is AreasResponse -> {
                        emit(
                            AreaSearchState.Success(areas = response.areas.toFlatAreas())
                        )
                    }
                }
            }

            else -> {
                emit(AreaSearchState.Error(error = Constants.NO_CONNECTION))
            }
        }
    }

    override fun getRegionById(id: String): Flow<AreaExtendedSearchState> = flow {
        val response = networkClient.getAreaById(AreaByIdRequest(id))

        when (response.resultCode) {
            Constants.HTTP_OK -> {
                when (response) {
                    is AreaExtendedResponse -> {
                        emit(
                            AreaExtendedSearchState.Success(area = response.toAreaExtended())
                        )
                    }
                }
            }

            else -> {
                emit(AreaExtendedSearchState.Error(error = Constants.NO_CONNECTION))
            }
        }
    }
}
