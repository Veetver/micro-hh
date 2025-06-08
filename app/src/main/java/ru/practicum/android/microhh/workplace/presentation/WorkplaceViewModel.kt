package ru.practicum.android.microhh.workplace.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.practicum.android.microhh.core.domain.models.Area
import ru.practicum.android.microhh.workplace.presentation.state.WorkplaceState

class WorkplaceViewModel(

) : ViewModel() {
    private val _state: MutableStateFlow<WorkplaceState> = MutableStateFlow(WorkplaceState())
    val state: StateFlow<WorkplaceState>
        get() = _state.asStateFlow()

    fun updateCounty(country: Area?) {
        if (_state.value.workplaceFilter.country != country) {
            _state.update { it.copy(workplaceFilter = it.workplaceFilter.copy(country = country), showApply = true) }
        }
    }
    fun updateRegion(region: Area?) {
        if (_state.value.workplaceFilter.region != region) {
            _state.update { it.copy(workplaceFilter = it.workplaceFilter.copy(region = region), showApply = true) }
        }
    }

    fun cancelChanges() {
        _state.update { WorkplaceState() }
    }
}
