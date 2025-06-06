package ru.practicum.android.microhh.industry.data.dto

import ru.practicum.android.microhh.core.data.dto.Response

data class IndustryResponse(
    val id: String,
    val name: String,
    val industries: List<IndustryItem>
)

data class IndustryItem(
    val id: String,
    val name: String
)

data class IndustryItemResponse(
    val items: List<IndustryResponse>
) : Response()
