package ru.practicum.android.microhh.core.domain.models

import com.google.gson.annotations.SerializedName

data class AreaExtended(
    @SerializedName("areas") val areas: List<AreaExtended>,
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("parent_id") val parentId: String?,
)
