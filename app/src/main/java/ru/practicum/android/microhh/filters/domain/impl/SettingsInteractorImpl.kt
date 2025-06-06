package ru.practicum.android.microhh.filters.domain.impl

import ru.practicum.android.microhh.filters.domain.api.SettingsInteractor
import ru.practicum.android.microhh.filters.domain.api.SettingsRepository
import ru.practicum.android.microhh.filters.domain.model.FilterSettings

class SettingsInteractorImpl(
    private val settingsRepository: SettingsRepository
) : SettingsInteractor {

    override val filterSettings = settingsRepository.filterSettings

    override fun updateSettings(newSettings: FilterSettings) {
        settingsRepository.updateSettings(newSettings)
    }

    override fun clearSettings() {
        settingsRepository.clearSettings()
    }
}
