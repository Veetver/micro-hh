package ru.practicum.android.microhh.search.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.microhh.core.data.dto.Response

data class VacancyResponse(
    val items: List<VacancyDto>,
    val found: Int,
    val pages: Int,
    val page: Int,
    @SerializedName("per_page") val perPage: Int,
    val clusters: Any?,
    val arguments: Any?,
    val fixes: Any?,
    val suggests: Any?,
    @SerializedName("alternate_url") val alternateUrl: String
) : Response()
