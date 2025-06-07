package ru.practicum.android.microhh.core.data.impl

import ru.practicum.android.microhh.core.data.dto.IndustryDtoConverter
import ru.practicum.android.microhh.core.domain.models.Industry
import ru.practicum.android.microhh.industry.data.dto.IndustryResponse

class IndustryDtoConverterImpl : IndustryDtoConverter {
    override fun toIndustryList(list: List<IndustryResponse>): List<Industry> {
        return list.map {
            Industry(
                id = it.id,
                name = it.name
            )
        }
    }
}
