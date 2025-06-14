package ru.practicum.android.microhh.industry.data.network

import retrofit2.http.GET
import ru.practicum.android.microhh.industry.data.dto.IndustryDto

interface HhIndustryService {

    @GET("/industries")
    suspend fun getIndustries(): List<IndustryDto>
}
