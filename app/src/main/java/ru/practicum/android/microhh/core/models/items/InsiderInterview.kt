package ru.practicum.android.microhh.core.models.items

import com.google.gson.annotations.SerializedName

data class InsiderInterview(
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String
)
