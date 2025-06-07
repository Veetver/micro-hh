package ru.practicum.android.microhh.industry.data.dto

import ru.practicum.android.microhh.core.data.dto.Response

data class IndustryResponse(
    val items: List<IndustryDto>
) : Response()
