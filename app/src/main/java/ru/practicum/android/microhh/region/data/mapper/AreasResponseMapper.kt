package ru.practicum.android.microhh.region.data.mapper

import ru.practicum.android.microhh.core.domain.models.AreaExtended
import ru.practicum.android.microhh.region.data.dto.response.AreaExtendedResponse

fun AreaExtendedResponse.toAreaExtended() = AreaExtended(
    areas = areas,
    id = id,
    name = name,
    parentId = parentId,
)
