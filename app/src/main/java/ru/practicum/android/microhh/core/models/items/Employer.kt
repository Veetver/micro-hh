package ru.practicum.android.microhh.core.models.items

data class Employer(
    val url: String,
    val alternate_url: String,
    val logo_urls: LogoUrls,
    val name: String,
    val id: String
)
