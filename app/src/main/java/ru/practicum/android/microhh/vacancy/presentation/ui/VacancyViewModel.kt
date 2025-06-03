package ru.practicum.android.microhh.vacancy.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.microhh.vacancy.data.VacancyDetailsDto
import ru.practicum.android.microhh.vacancy.domain.VacancyInteractor

class VacancyViewModel(private val vacancyInteractor: VacancyInteractor) : ViewModel() {

    private val _stateFlow = MutableStateFlow<VacancyState>(VacancyState.Loading)
    val stateFlow: StateFlow<VacancyState> = _stateFlow.asStateFlow()

    fun getVacancyById(vacancyId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            doRequest(vacancyId)
        }
    }

    private fun updateState(state: VacancyState) {
        _stateFlow.update {
            state
        }
    }

    private fun doRequest(term: String?) {
        if (term.isNullOrEmpty()) return

        updateState(VacancyState.Loading)

        viewModelScope.launch {
            vacancyInteractor(term)
                .collect { result ->
                    result.vacancy?.let { processResult(it, result.error, result.term) }
                }
        }
    }

    private fun processResult(vacancy: VacancyDetailsDto, error: Int?, term: String) {
        updateState(
            when {
                // error != null -> VacancyState.ConnectionError(error, term)
                else ->
                    VacancyState.ShowDetails(vacancy, term)
            }
        )
    }
}
