package ru.practicum.android.microhh.core.domain.models

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("city") val city: String
)
