package ru.practicum.android.microhh.workplace.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.microhh.core.domain.models.Catalog
import ru.practicum.android.microhh.country.presentation.mapper.toCatalog
import ru.practicum.android.microhh.filters.domain.api.SettingsRepository
import ru.practicum.android.microhh.region.domain.impl.GetRegionByIdUseCase
import ru.practicum.android.microhh.region.domain.mapper.toArea
import ru.practicum.android.microhh.workplace.domain.models.WorkplaceFilter
import ru.practicum.android.microhh.workplace.presentation.state.WorkplaceState

class WorkplaceViewModel(
    settingsRepository: SettingsRepository,
    private val getRegionByIdUseCase: GetRegionByIdUseCase
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
        var country: Catalog? = null
        region?.let {
            viewModelScope.launch {
                var parentId: String? = getRegionByIdUseCase(region.id).first().area?.parentId
                while (parentId.isNullOrEmpty().not()) {
                    val parent = getRegionByIdUseCase(parentId).first().area
                    parentId = parent?.parentId
                    country = parent?.toArea()?.toCatalog()
                }
            }.invokeOnCompletion {
                if (region != _state.value.workplaceFilter.region) {
                    _state.update {
                        val newWorkplaceFilter = it.workplaceFilter.copy(region = region, country = country)
                        it.copy(
                            workplaceFilter = newWorkplaceFilter,
                            showApply = settings.workplace != newWorkplaceFilter
                        )
                    }
                }
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
