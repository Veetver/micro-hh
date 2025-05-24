package ru.practicum.android.microhh.core.models

import ru.practicum.android.microhh.core.models.items.Vacancy
import com.google.gson.annotations.SerializedName

data class VacancyResponse(
    @SerializedName("items") val items: List<Vacancy>,
    @SerializedName("found") val found: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("per_page") val perPage: Int,
    @SerializedName("clusters") val clusters: Any?,
    @SerializedName("arguments") val arguments: Any?,
    @SerializedName("fixes") val fixes: Any?,
    @SerializedName("suggests") val suggests: Any?,
    @SerializedName("alternate_url") val alternateUrl: String
)
