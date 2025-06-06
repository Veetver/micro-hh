package ru.practicum.android.microhh.core.resources

import ru.practicum.android.microhh.filters.domain.model.FilterSettings

class FiltersState(
    val filters: FilterSettings = FilterSettings(),
    val updateState: Boolean = false
)
