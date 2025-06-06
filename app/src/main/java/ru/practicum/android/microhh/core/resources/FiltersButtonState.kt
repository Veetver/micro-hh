package ru.practicum.android.microhh.core.resources

sealed class FiltersButtonState(
    val isVisible: Boolean = false
) {

    data object Default: FiltersButtonState()
    class Apply(isVisible: Boolean): FiltersButtonState(isVisible)
    class Clear(isVisible: Boolean): FiltersButtonState(isVisible)
}
