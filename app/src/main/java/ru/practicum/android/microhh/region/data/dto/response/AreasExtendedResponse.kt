package ru.practicum.android.microhh.region.data.dto.response

import ru.practicum.android.microhh.core.data.dto.Response
import ru.practicum.android.microhh.core.domain.models.AreaExtended

class AreaExtendedResponse(
    val areas: List<AreaExtended>,
    val id: String,
    val name: String,
    val parentId: String?,
): Response()
