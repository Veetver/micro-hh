package ru.practicum.android.microhh.region.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.microhh.core.domain.models.Catalog
import ru.practicum.android.microhh.core.resources.CatalogSearchState
import ru.practicum.android.microhh.country.presentation.mapper.toCatalog
import ru.practicum.android.microhh.country.presentation.state.CountryState
import ru.practicum.android.microhh.filters.presentation.FiltersViewModel
import ru.practicum.android.microhh.region.domain.impl.GetRegionByIdUseCase
import ru.practicum.android.microhh.region.domain.impl.GetRegionsWOCountriesUseCase
import ru.practicum.android.microhh.region.presentation.state.RegionState

class RegionViewModel(
    private val currentCountry: Catalog? = null,
    private val getRegionByIdUseCase: GetRegionByIdUseCase,
    private val getRegionsWOCountriesUseCase: GetRegionsWOCountriesUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<RegionState> = MutableStateFlow(RegionState())
    val state: StateFlow<RegionState>
        get() = _state.asStateFlow()

    private val list = mutableListOf<Catalog>()
    private val originalList = mutableListOf<Catalog>()
    private val filteredList = mutableListOf<Catalog>()

    init {
        getRegions()
    }

    fun filter(searchQuery: String?) {
        filteredList.clear()
        if (searchQuery.isNullOrEmpty()) {
            _state.update { it.copy(isLoading = false, regions = originalList, error = null) }
        } else {
            _state.update {
                it.copy(
                    isLoading = false,
                    regions = originalList.filter { item -> item.name.contains(searchQuery, ignoreCase = true) },
                    error = null
                )
            }
        }
    }

    private fun updateDisplayList(updatedList: List<Catalog>) {
        list.clear()
        list.addAll(updatedList)
        _state.update { it.copy(regions = list) }
    }

    private fun getRegions() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }

            getRegionsWOCountriesUseCase()
                .collect { result ->
                    Log.d("TAG", "getRegions: ${result.areas}")
                    originalList.clear()
                    originalList.addAll(result.areas.map { it.toCatalog() })
                    processResult(result.areas.map { it.toCatalog() }, result.error)
                }
        }
    }

    private fun processResult(regions: List<Catalog>, error: Int?) {
        when {
            regions.isNotEmpty() -> _state.update { it.copy(regions = regions, error = null, isLoading = false) }
            error != null -> _state.update { it.copy(error = error, regions = emptyList(), isLoading = false) }
            else -> _state.update { it.copy(error = null, regions = regions, isLoading = false) }
        }
    }
}
