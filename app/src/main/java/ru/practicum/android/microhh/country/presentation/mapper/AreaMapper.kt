package ru.practicum.android.microhh.country.presentation.mapper

import ru.practicum.android.microhh.core.domain.models.Area
import ru.practicum.android.microhh.core.domain.models.Catalog

fun Area.toCatalog(): Catalog = Catalog(
    id = id,
    name = name,
)
