package ru.practicum.android.microhh.core.models.items

import com.google.gson.annotations.SerializedName

data class Salary(
    @SerializedName("to") val to: Int?,
    @SerializedName("from") val from: Int?,
    @SerializedName("currency") val currency: String,
    @SerializedName("gross") val gross: Boolean
)
