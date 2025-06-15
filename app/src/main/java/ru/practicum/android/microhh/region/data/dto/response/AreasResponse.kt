package ru.practicum.android.microhh.region.data.dto.response

import ru.practicum.android.microhh.core.data.dto.Response
import ru.practicum.android.microhh.core.domain.models.AreaExtended

class AreasResponse(
    val areas: List<AreaExtended>,
) : Response()
