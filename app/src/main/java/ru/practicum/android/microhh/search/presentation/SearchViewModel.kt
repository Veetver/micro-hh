package ru.practicum.android.microhh.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.microhh.core.resources.SearchState
import ru.practicum.android.microhh.core.utils.Constants
import ru.practicum.android.microhh.core.utils.Debounce
import ru.practicum.android.microhh.search.data.dto.VacancyDto
import ru.practicum.android.microhh.search.domain.impl.VacancySearchUseCase

class SearchViewModel(
    private val vacancySearchUseCase: VacancySearchUseCase
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<SearchState>(SearchState.NoData)
    val stateFlow: StateFlow<SearchState> = _stateFlow.asStateFlow()

    private val timer: Debounce<String> by lazy {
        Debounce(Constants.USER_INPUT_DELAY, viewModelScope) { term ->
            doSearch(term)
        }
    }

    fun search(term: String) {
        timer.start(parameter = term)
    }

    private fun updateState(state: SearchState) {
        _stateFlow.update {
            state
        }
    }

    private fun doSearch(term: String?) {
        if (term.isNullOrEmpty()) return

        updateState(SearchState.Loading)
        viewModelScope.launch {
            vacancySearchUseCase(term)
                .collect { result ->
                    processResult(result.vacancies, result.error, result.count, result.term)
                }
        }
    }

    private fun processResult(vacancies: List<VacancyDto>, error: Int?, count: Int, term: String) {
        updateState(
            when {
                vacancies.isNotEmpty() -> {
                    SearchState.SearchResults(vacancies, count, term)
                }
                // error != null -> SearchState.ConnectionError(error, term)
                else -> SearchState.NothingFound(term)
            }
        )
    }

    override fun onCleared() {
    }
}
