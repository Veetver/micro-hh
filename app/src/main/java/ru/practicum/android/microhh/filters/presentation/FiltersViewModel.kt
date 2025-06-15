package ru.practicum.android.microhh.filters.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import ru.practicum.android.microhh.core.domain.models.Catalog
import ru.practicum.android.microhh.core.resources.FiltersButtonState
import ru.practicum.android.microhh.core.utils.Extensions.isEmpty
import ru.practicum.android.microhh.filters.domain.api.SettingsInteractor
import ru.practicum.android.microhh.filters.domain.model.FilterSettings
import ru.practicum.android.microhh.workplace.domain.models.WorkplaceFilter

class FiltersViewModel(
    private val settingsInteractor: SettingsInteractor
) : ViewModel() {

    fun filterSettings() = settingsInteractor.filterSettings()
    private val _filtersStateFlow: MutableStateFlow<FilterSettings> = MutableStateFlow(filterSettings())
    val filtersStateFlow: StateFlow<FilterSettings> = _filtersStateFlow.asStateFlow()
    val buttonsStateFlow: StateFlow<FiltersButtonState> = _filtersStateFlow
        .map { state ->
            FiltersButtonState(
                isApplyVisible = filterSettings() != state,
                isClearVisible = state.isEmpty().not(),
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = FiltersButtonState()
        )

    fun updateSettings() {
        settingsInteractor.updateSettings(_filtersStateFlow.value)
    }

    fun setWorkplaceFilter(workplaceFilter: WorkplaceFilter?) {
        _filtersStateFlow.update {
            it.copy(workplace = workplaceFilter)
        }
    }

    fun setIndustryFilter(industry: Catalog?) {
        _filtersStateFlow.update {
            it.copy(industry = industry)
        }
    }

    fun setSalaryFilter(salary: String?) {
        _filtersStateFlow.update {
            it.copy(salary = salary)
        }
    }

    fun setShowNoSalary(show: Boolean) {
        _filtersStateFlow.update {
            it.copy(showWithoutSalary = show)
        }
    }

    fun clearSettings() {
        _filtersStateFlow.update { FilterSettings() }
    }

    fun cancelChanges() {
        _filtersStateFlow.update { filterSettings() }
    }
}
