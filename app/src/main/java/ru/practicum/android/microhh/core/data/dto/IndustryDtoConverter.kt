package ru.practicum.android.microhh.core.data.dto

import ru.practicum.android.microhh.core.domain.models.Industry
import ru.practicum.android.microhh.industry.data.dto.IndustryResponse

interface IndustryDtoConverter {
    fun toIndustryList(list: List<IndustryResponse>): List<Industry>
}
