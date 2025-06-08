package ru.practicum.android.microhh.region.domain.mapper

import ru.practicum.android.microhh.core.domain.models.Area
import ru.practicum.android.microhh.core.domain.models.AreaExtended

fun AreaExtended.toArea() = Area(
    url = "https://api.hh.ru/areas/$id",
    id = id,
    name = name
)
