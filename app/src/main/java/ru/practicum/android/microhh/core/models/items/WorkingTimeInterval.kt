package ru.practicum.android.microhh.core.models.items

import com.google.gson.annotations.SerializedName

data class WorkingTimeInterval(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)
