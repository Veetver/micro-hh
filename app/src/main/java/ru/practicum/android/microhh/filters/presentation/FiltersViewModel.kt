package ru.practicum.android.microhh.filters.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.practicum.android.microhh.filters.domain.api.SettingsInteractor
import ru.practicum.android.microhh.filters.domain.model.FilterSettings

class FiltersViewModel(
    private val settingsInteractor: SettingsInteractor
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(getSettings())
    val stateFlow: StateFlow<FilterSettings> = _stateFlow.asStateFlow()

    fun getSettings(): FilterSettings {
        return settingsInteractor.filterSettings
    }

    fun updateSettings(updateSettings: Boolean, newSettings: FilterSettings) {
        if (updateSettings) settingsInteractor.updateSettings(newSettings)
        updateState(newSettings)
    }

    fun clearSettings() {
        settingsInteractor.clearSettings()
        updateState(FilterSettings())
    }

    private fun updateState(state: FilterSettings) {
        _stateFlow.update {
            state
        }
    }
}
