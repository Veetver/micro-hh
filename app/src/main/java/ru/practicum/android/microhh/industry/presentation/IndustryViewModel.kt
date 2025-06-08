package ru.practicum.android.microhh.industry.presentation

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
import ru.practicum.android.microhh.industry.domain.impl.IndustryListUseCase

class IndustryViewModel(
    private val industryListUseCase: IndustryListUseCase,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<CatalogSearchState>(CatalogSearchState.Loading)
    val stateFlow: StateFlow<CatalogSearchState> = _stateFlow.asStateFlow()

    private val list = mutableListOf<Catalog>()
    private val originalList = mutableListOf<Catalog>()
    private val filteredList = mutableListOf<Catalog>()
    private var _catalog: Catalog = Catalog()
    val catalog
        get() = _catalog

    init {
        viewModelScope.launch(Dispatchers.IO) {
            doRequest()
        }
    }

    fun onIndustrySelected(catalog: Catalog) {
        _catalog = catalog
    }

    fun filter(searchQuery: String?) {
        filteredList.clear()
        if (searchQuery.isNullOrEmpty()) {
            updateDisplayList(originalList)
        } else {
            originalList.forEach {
                if (it.name.contains(searchQuery, true)) {
                    filteredList.add(it)
                }
            }
            updateDisplayList(filteredList)
        }
    }

    private fun updateDisplayList(updatedList: List<Catalog>) {
        list.clear()
        list.addAll(updatedList)
        updateState(CatalogSearchState.Results(list))
    }

    private suspend fun doRequest() {
        updateState(CatalogSearchState.Loading)

        industryListUseCase()
            .collect { result ->
                processResult(result.catalog, result.error)
            }
    }

    private fun processResult(catalog: List<Catalog>, error: Int?) {
        updateState(
            when {
                catalog.isNotEmpty() -> {
                    originalList.clear()
                    originalList.addAll(catalog)
                    CatalogSearchState.Results(catalog)
                }
                error != null -> CatalogSearchState.NoData(error)
                else -> CatalogSearchState.Default
            }
        )
    }

    private fun updateState(state: CatalogSearchState) {
        _stateFlow.update {
            state
        }
    }
}
