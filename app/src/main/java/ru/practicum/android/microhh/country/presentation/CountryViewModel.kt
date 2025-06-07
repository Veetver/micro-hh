package ru.practicum.android.microhh.country.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.microhh.core.domain.models.Country
import ru.practicum.android.microhh.country.domain.impl.CountrySearchUseCase
import ru.practicum.android.microhh.country.presentation.ui.CountryState

class CountryViewModel(
    private val countrySearchUseCase: CountrySearchUseCase
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

            countrySearchUseCase()
                .collect { result ->
                    processResult(result.countries, result.error, result.term)
                }
        }
    }

    private fun processResult(countries: List<Country>, error: Int?, term: String) {
        updateState(
            when {
                countries.isNotEmpty() -> {
                    CountryState.ShowCountries(countries, term)
                }

                error != null -> CountryState.ConnectionError(error, term)
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

