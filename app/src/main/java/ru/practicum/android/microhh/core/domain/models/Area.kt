package ru.practicum.android.microhh.core.domain.models

import com.google.gson.annotations.SerializedName

data class Area(
    @SerializedName("url") val url: String,
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)
