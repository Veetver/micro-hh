package ru.practicum.android.microhh.region.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.microhh.region.presentation.state.RegionState

class RegionViewModel() : ViewModel() {
    private val _state: MutableStateFlow<RegionState> = MutableStateFlow(RegionState.Loading)
    val state: StateFlow<RegionState>
        get() = _state.asStateFlow()
}
