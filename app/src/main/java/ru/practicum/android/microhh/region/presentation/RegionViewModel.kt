package ru.practicum.android.microhh.region.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.microhh.core.domain.models.Catalog
import ru.practicum.android.microhh.country.presentation.mapper.toCatalog
import ru.practicum.android.microhh.region.domain.impl.GetRegionByIdUseCase
import ru.practicum.android.microhh.region.domain.impl.GetRegionsWOCountriesUseCase
import ru.practicum.android.microhh.region.domain.mapper.toArea
import ru.practicum.android.microhh.region.presentation.state.RegionState

class RegionViewModel(
    currentCountry: Catalog? = null,
    private val getRegionByIdUseCase: GetRegionByIdUseCase,
    private val getRegionsWOCountriesUseCase: GetRegionsWOCountriesUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<RegionState> = MutableStateFlow(RegionState())
    val state: StateFlow<RegionState>
        get() = _state.asStateFlow()

    private val originalList = mutableListOf<Catalog>()
    private val filteredList = mutableListOf<Catalog>()

    init {
        getRegions(currentCountry?.id)
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

    private fun getRegions(countryId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }

            if (!countryId.isNullOrEmpty()) {
                viewModelScope.launch {
                    getRegionByIdUseCase(countryId).collect { result ->
                        originalList.clear()
                        result.area?.areas?.let { areaExtended ->
                            val catalogList = areaExtended.map { it.toArea().toCatalog() }
                            originalList.addAll(catalogList)
                            processResult(
                                regions = catalogList,
                                error = result.error
                            )
                        }
                    }
                }
            } else {
                getRegionsWOCountriesUseCase()
                    .collect { result ->
                        originalList.clear()
                        originalList.addAll(result.areas.map { it.toCatalog() })
                        processResult(result.areas.map { it.toCatalog() }, result.error)
                    }
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
