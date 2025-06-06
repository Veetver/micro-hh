package ru.practicum.android.microhh.country.data.dto.response

import ru.practicum.android.microhh.core.data.dto.Response
import ru.practicum.android.microhh.core.domain.models.Area

class AreasResponse(
    val areas: List<Area>
): Response()
