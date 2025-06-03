package ru.practicum.android.microhh.core.resources

import android.view.View
import androidx.core.view.isVisible

sealed interface VisibilityState {
    data object Placeholder : VisibilityState
    data object Error : VisibilityState
    data object NoData : VisibilityState
    data object Results : VisibilityState
    class VisibilityItem(
        val view: View,
        val type: VisibilityState,
    )
    class ViewsList(
        val items: List<VisibilityItem>,
    ) {

        fun show(state: VisibilityState, list: List<VisibilityItem> = items) {
            list.forEach {
                it.view.isVisible = it.type == state
            }
        }
    }
}
