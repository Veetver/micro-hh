package ru.practicum.android.microhh.core.resources

import ru.practicum.android.microhh.core.domain.models.Industry

sealed class IndustryState(
    val industries: List<Industry>? = null,
    val error: Int? = null,
) {
    data object Loading : IndustryState(null)

    class Success(
        industries: List<Industry>,
    ) : IndustryState(industries)

    class Error(error: Int?) : IndustryState(error = error)
}
