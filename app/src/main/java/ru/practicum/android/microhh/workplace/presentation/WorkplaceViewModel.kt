package ru.practicum.android.microhh.workplace.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.practicum.android.microhh.core.domain.models.Catalog
import ru.practicum.android.microhh.filters.domain.api.SettingsRepository
import ru.practicum.android.microhh.workplace.domain.models.WorkplaceFilter
import ru.practicum.android.microhh.workplace.presentation.state.WorkplaceState

class WorkplaceViewModel(
    settingsRepository: SettingsRepository,
) : ViewModel() {
    private var settings = settingsRepository.filterSettings()

    private val _state: MutableStateFlow<WorkplaceState> = MutableStateFlow(
        WorkplaceState(workplaceFilter = settings.workplace ?: WorkplaceFilter())
    )
    val state: StateFlow<WorkplaceState>
        get() = _state.asStateFlow()

    fun updateCountry(country: Catalog?) {
        if (country != _state.value.workplaceFilter.country) {
            _state.update {
                val newWorkplaceFilter = WorkplaceFilter(country = country)
                it.copy(
                    workplaceFilter = newWorkplaceFilter,
                    showApply = settings.workplace != newWorkplaceFilter
                )
            }
        }
    }

    fun updateRegion(region: Catalog?) {
        if (region != _state.value.workplaceFilter.region) {
            _state.update {
                val newWorkplaceFilter = it.workplaceFilter.copy(region = region)
                it.copy(
                    workplaceFilter = newWorkplaceFilter,
                    showApply = settings.workplace != newWorkplaceFilter
                )
            }
        }
    }

    fun onClearedCountry() {
        _state.update {
            val isNotNull = settings.workplace != null
            val notEquals = settings.workplace != _state.value.workplaceFilter
            WorkplaceState(
                showApply = isNotNull && notEquals
            )
        }
    }

    fun applied() {
        _state.update { it.copy(showApply = false) }
    }

    fun onClearedRegion() {
        _state.update {
            WorkplaceState(
                workplaceFilter = it.workplaceFilter.copy(region = null),
                showApply = true,
            )
        }
    }

    fun cancelChanges() {
        _state.update { WorkplaceState(workplaceFilter = settings.workplace ?: WorkplaceFilter()) }
    }
}
