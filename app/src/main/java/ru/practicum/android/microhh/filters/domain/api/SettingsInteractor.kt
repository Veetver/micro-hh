package ru.practicum.android.microhh.filters.domain.api

import ru.practicum.android.microhh.filters.domain.model.FilterSettings

interface SettingsInteractor {
    fun filterSettings(): FilterSettings
    fun updateSettings(newSettings: FilterSettings)
    fun clearSettings()
}
