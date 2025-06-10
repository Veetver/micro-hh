package ru.practicum.android.microhh.region.presentation.state

import ru.practicum.android.microhh.core.domain.models.Catalog

data class RegionState(
    val isLoading: Boolean = false,
    val currentCountry: Catalog? = null,
    val regions: List<Catalog> = emptyList(),
    val error: Int? = null,
)
