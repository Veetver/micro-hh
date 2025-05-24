package ru.practicum.android.microhh.core.models

import ru.practicum.android.microhh.core.models.items.Vacancy

data class VacancyResponse(
    val items: List<Vacancy>,
    val found: Int,
    val pages: Int,
    val page: Int,
    val per_page: Int,
    val clusters: Any?,
    val arguments: Any?,
    val fixes: Any?,
    val suggests: Any?,
    val alternate_url: String
)
