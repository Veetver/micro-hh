package ru.practicum.android.microhh.filters.data.impl

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import ru.practicum.android.microhh.core.utils.Constants
import ru.practicum.android.microhh.filters.domain.api.SettingsRepository
import ru.practicum.android.microhh.filters.domain.model.FilterSettings

class SettingsRepositoryImpl(
    private val prefs: SharedPreferences,
    private val gson: Gson,
) : SettingsRepository {

    private var _filterSettings = loadFilterSettings()
    override val filterSettings: FilterSettings
        get() = _filterSettings

    private fun loadFilterSettings(): FilterSettings {
        val prefsSettings = prefs.getString(Constants.KEY_FILTERS, null)

        return if (!prefsSettings.isNullOrBlank()) {
            gson.fromJson(prefsSettings, FilterSettings::class.java)
        } else {
            FilterSettings()
        }
    }

    private fun saveSettings() {
        prefs.edit {
            putString(Constants.KEY_FILTERS, gson.toJson(_filterSettings))
        }
    }

    override fun updateSettings(newSettings: FilterSettings) {
        _filterSettings = newSettings
        saveSettings()
    }

    override fun clearSettings() {
        _filterSettings = FilterSettings()
        saveSettings()
    }
}
