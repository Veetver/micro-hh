package ru.practicum.android.microhh.filters.domain.model

import ru.practicum.android.microhh.core.domain.models.Catalog
import ru.practicum.android.microhh.workplace.domain.models.WorkplaceFilter

data class FilterSettings(
    val workplace: WorkplaceFilter? = null,
    val industry: Catalog? = null,
    val salary: String? = null,
    val showWithoutSalary: Boolean = false,
)
