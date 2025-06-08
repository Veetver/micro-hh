package ru.practicum.android.microhh.filters.domain.model

import kotlinx.serialization.Serializable
import ru.practicum.android.microhh.workplace.domain.models.WorkplaceFilter

@Serializable
data class FilterSettings(
    val workplace: WorkplaceFilter = WorkplaceFilter(),
    val industry: String = "",
    val salary: String = "",
    val showWithoutSalary: Boolean = false,
)
