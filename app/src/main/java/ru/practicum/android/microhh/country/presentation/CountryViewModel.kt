package ru.practicum.android.microhh.country.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.microhh.core.domain.models.Area
import ru.practicum.android.microhh.country.domain.impl.GetCountriesUseCase
import ru.practicum.android.microhh.country.presentation.state.CountryState

class CountryViewModel(
    private val getCountriesUseCase: GetCountriesUseCase,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<CountryState>(CountryState.Loading)
    val stateFlow: StateFlow<CountryState> = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getCountries()
        }
    }

    private fun getCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            updateState(CountryState.Loading)

            getCountriesUseCase()
                .collect { result ->
                    processResult(result.areas, result.error)
                }
        }
    }

    private fun processResult(countries: List<Area>, error: Int?) {
        updateState(
            when {
                countries.isNotEmpty() -> {
                    CountryState.ShowCountries(countries)
                }

                error != null -> CountryState.ConnectionError(error)
                else -> CountryState.NoCountries
            }
        )
    }

    private fun updateState(state: CountryState) {
        _stateFlow.update {
            state
        }
    }
}

