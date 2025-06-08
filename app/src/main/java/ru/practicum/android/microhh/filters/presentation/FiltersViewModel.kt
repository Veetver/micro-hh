package ru.practicum.android.microhh.filters.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.microhh.core.resources.FiltersButtonState
import ru.practicum.android.microhh.core.resources.FiltersState
import ru.practicum.android.microhh.core.utils.Extensions.isEmpty
import ru.practicum.android.microhh.filters.domain.api.SettingsInteractor
import ru.practicum.android.microhh.filters.domain.model.FilterSettings

class FiltersViewModel(
    private val settingsInteractor: SettingsInteractor
) : ViewModel() {

    private var _filterSettings = settingsInteractor.filterSettings
    private val _filtersStateFlow = MutableSharedFlow<FiltersState>()
    val filtersStateFlow: SharedFlow<FiltersState> = _filtersStateFlow

    private val _buttonsStateFlow = MutableStateFlow<FiltersButtonState>(FiltersButtonState.Default)
    val buttonsStateFlow: StateFlow<FiltersButtonState> = _buttonsStateFlow.asStateFlow()

    fun getSettings(): FilterSettings {
        return settingsInteractor.filterSettings
    }

    fun updateSettings(
        newSettings: FilterSettings,
        updateSettings: Boolean,
        updateFiltersState: Boolean,
    ) {
        if (updateSettings) settingsInteractor.updateSettings(newSettings)
        setSettingsState(FiltersState(newSettings, updateFiltersState))
        updateButtons(newSettings)
    }

    fun clearSettings() {
        updateButtons(FilterSettings())
        settingsInteractor.clearSettings()
        _filterSettings = FilterSettings()
        setSettingsState(FiltersState(_filterSettings, true))
    }

    private fun updateButtons(newSettings: FilterSettings) {
        setButtonsState(FiltersButtonState.Apply(_filterSettings != newSettings))
        setButtonsState(FiltersButtonState.Clear(newSettings.isEmpty().not()))
    }

    private fun setSettingsState(state: FiltersState) {
        viewModelScope.launch {
            _filtersStateFlow.emit(state)
        }
    }

    private fun setButtonsState(state: FiltersButtonState) {
        _buttonsStateFlow.update {
            state
        }
    }
}
