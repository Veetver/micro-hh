package ru.practicum.android.microhh.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.microhh.core.domain.models.Vacancy
import ru.practicum.android.microhh.core.resources.SearchState
import ru.practicum.android.microhh.core.utils.Constants
import ru.practicum.android.microhh.core.utils.Debounce
import ru.practicum.android.microhh.filters.domain.api.SettingsRepository
import ru.practicum.android.microhh.filters.domain.model.FilterSettings
import ru.practicum.android.microhh.search.domain.impl.VacancySearchUseCase

class SearchViewModel(
    private val vacancySearchUseCase: VacancySearchUseCase,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<SearchState>(SearchState.NoData)
    val stateFlow: StateFlow<SearchState> = _stateFlow.asStateFlow()

    private val vacanciesList = mutableListOf<Vacancy>()
    private var currentPage = 1
    private var maxPages = 2
    private var isNextPageLoading = false
    private var isNextPage = false
    private val filters: FilterSettings
        get() = settingsRepository.filterSettings
    private val canLoadMore
        get() = currentPage != maxPages

    private val timer: Debounce<String> by lazy {
        Debounce(Constants.USER_INPUT_DELAY, viewModelScope) { term ->
            doSearch(term)
        }
    }

    fun updateList(list: List<Vacancy>): List<Vacancy> {
        vacanciesList.addAll(list)
        return vacanciesList.toList()
    }

    fun search(term: String, isNextPage: Boolean = false) {
        this.isNextPage = isNextPage

        if (!isNextPage) {
            timer.start(parameter = term)
        } else {
            doSearch(term)
        }
    }

    private fun updateState(state: SearchState) {
        _stateFlow.update {
            state
        }
    }

    private fun doSearch(term: String?) {
        if (term.isNullOrEmpty()) {
            return
        }

        if (!isNextPage) {
            updateState(SearchState.Loading)
            vacanciesList.clear()
            currentPage = 1
        }

        if (isNextPageLoading || !canLoadMore) return

        isNextPageLoading = true
        viewModelScope.launch {
            vacancySearchUseCase(term, currentPage, filters)
                .collect { result ->
                    currentPage++
                    maxPages = result.pagesCount
                    processResult(result.vacancies, result.error, result.vacanciesCount, result.term)
                }
        }
    }

    private fun processResult(vacancies: List<Vacancy>, error: Int?, count: Int, term: String?) {
        updateState(
            when {
                vacancies.isNotEmpty() -> {
                    if (!isNextPage) {
                        SearchState.SearchResults(vacancies, count, term, canLoadMore)
                    } else {
                        SearchState.NextPage(vacancies, count, canLoadMore)
                    }
                }
                error != null -> SearchState.ConnectionError(error, isNextPage, term)
                else -> SearchState.NothingFound(term)
            }
        )
        isNextPageLoading = false
    }
}
