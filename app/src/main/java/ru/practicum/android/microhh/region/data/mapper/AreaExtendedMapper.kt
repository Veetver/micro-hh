package ru.practicum.android.microhh.region.data.mapper

import ru.practicum.android.microhh.core.domain.models.Area
import ru.practicum.android.microhh.core.domain.models.AreaExtended
import ru.practicum.android.microhh.region.domain.mapper.toArea

fun List<AreaExtended>.toFlatAreas(): List<Area> =
    flatMap { areaExtended ->
        if (areaExtended.areas.isNotEmpty()) {
            areaExtended.areas.toFlatAreas()
        } else {
            listOf(areaExtended.toArea())
        }
    }
