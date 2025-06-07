package ru.practicum.android.microhh.core.domain.models

import com.google.gson.annotations.SerializedName

data class Employer(
    @SerializedName("url") val url: String,
    @SerializedName("alternate_url") val alternateUrl: String,
    @SerializedName("logo_urls") val logoUrls: LogoUrls?,
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: String
)
