package ru.practicum.android.microhh.filters.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.practicum.android.microhh.core.resources.FiltersButtonState
import ru.practicum.android.microhh.core.resources.FiltersState
import ru.practicum.android.microhh.filters.domain.api.SettingsInteractor
import ru.practicum.android.microhh.filters.domain.model.FilterSettings

class FiltersViewModel(
    private val settingsInteractor: SettingsInteractor
) : ViewModel() {

    private var _filterSettings = settingsInteractor.filterSettings
    val filterSettings = _filterSettings

    private val _filtersStateFlow = MutableStateFlow(FiltersState(_filterSettings, true))
    val filtersStateFlow: StateFlow<FiltersState> = _filtersStateFlow.asStateFlow()

    private val _buttonsStateFlow = MutableStateFlow<FiltersButtonState>(FiltersButtonState.Default)
    val buttonsStateFlow: StateFlow<FiltersButtonState> = _buttonsStateFlow.asStateFlow()

    init {
        updateButtons(_filterSettings)
    }

    fun updateButtons(newSettings: FilterSettings) {
        setButtonsState(FiltersButtonState.Apply(_filterSettings != newSettings))
        setButtonsState(FiltersButtonState.Clear(newSettings.isEmpty().not()))
    }

    fun updateSettings(
        newSettings: FilterSettings,
        updateSettings: Boolean,
        updateFiltersState: Boolean,
    ) {
        if (updateSettings) settingsInteractor.updateSettings(newSettings)
        setSettingsState(FiltersState(newSettings, updateFiltersState))
    }

    fun clearSettings() {
        updateButtons(FilterSettings())
        settingsInteractor.clearSettings()
        _filterSettings = FilterSettings()
        setSettingsState(FiltersState(_filterSettings, true))
    }

    private fun setSettingsState(state: FiltersState) {
        _filtersStateFlow.update {
            state
        }
    }

    private fun setButtonsState(state: FiltersButtonState) {
        _buttonsStateFlow.update {
            state
        }
    }
}
