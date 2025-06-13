package ru.practicum.android.microhh.region.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.practicum.android.microhh.core.domain.models.AreaExtended

interface HhRegionService {

    @GET("/areas")
    suspend fun getAreas(): List<AreaExtended>

    @GET("/areas/{area_id}")
    suspend fun getAreaById(
        @Path("area_id") id: String
    ): AreaExtended
}
