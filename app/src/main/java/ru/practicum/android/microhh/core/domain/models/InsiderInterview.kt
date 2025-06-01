package ru.practicum.android.microhh.core.domain.models

import com.google.gson.annotations.SerializedName

data class InsiderInterview(
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String
)
