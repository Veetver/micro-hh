package ru.practicum.android.microhh.core.domain.models

class AreaExtended(
    val areas: List<AreaExtended>,
    val id: String,
    val name: String,
    val parentId: String?,
)
