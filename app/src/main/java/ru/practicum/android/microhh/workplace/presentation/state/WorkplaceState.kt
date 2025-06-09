package ru.practicum.android.microhh.workplace.presentation.state

import ru.practicum.android.microhh.workplace.domain.models.WorkplaceFilter

data class WorkplaceState(
    val workplaceFilter: WorkplaceFilter = WorkplaceFilter(),
    val showApply: Boolean = false,
)
