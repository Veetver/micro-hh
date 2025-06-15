package ru.practicum.android.microhh.industry.data.dto

data class IndustryDto(
    val id: String,
    val industries: List<IndustryDto>,
    val name: String,
)
