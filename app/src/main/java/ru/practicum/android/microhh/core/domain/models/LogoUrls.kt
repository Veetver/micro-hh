package ru.practicum.android.microhh.core.domain.models

import com.google.gson.annotations.SerializedName

data class LogoUrls(
    @SerializedName("90") val size90: String,
    @SerializedName("240") val size240: String,
    @SerializedName("original") val original: String
)
