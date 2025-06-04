package ru.practicum.android.microhh.vacancy.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.microhh.core.domain.interactors.favorites.FavoriteJobInteractor
import ru.practicum.android.microhh.core.domain.models.VacancyDetails
import ru.practicum.android.microhh.vacancy.domain.impl.VacancyDetailsUseCase
import ru.practicum.android.microhh.vacancy.presentation.models.VacancyDetailsUi
import ru.practicum.android.microhh.vacancy.presentation.models.toJobInfo

class VacancyViewModel(
    private val vacancyId: String,
    private val vacancyDetailsUseCase: VacancyDetailsUseCase,
    private val interactor: FavoriteJobInteractor
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<VacancyState>(VacancyState.Loading)
    val stateFlow: StateFlow<VacancyState> = _stateFlow.asStateFlow()
    private val _stateFavoriteFlow = MutableStateFlow<VacancyFavoriteState>(VacancyFavoriteState.Loading)
    val stateFavoriteFlow: StateFlow<VacancyFavoriteState> = _stateFavoriteFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            doRequest(vacancyId)
        }
        viewModelScope.launch(Dispatchers.IO) {
            observeFavoriteStatus(vacancyId)
        }
    }

    private fun observeFavoriteStatus(vacancyId: String) {
        viewModelScope.launch {
            val id = runCatching { vacancyId.toLong() }.getOrNull()
            if (id == null) {
                _stateFavoriteFlow.value = VacancyFavoriteState.Error
            } else {
                val jobInfo = interactor.findById(id).first()
                _stateFavoriteFlow.value = if (jobInfo != null) {
                    VacancyFavoriteState.VacancyFavorite
                } else {
                    VacancyFavoriteState.VacancyNotFavorite
                }
            }
        }
    }

    fun updateFavorite(vacancy: VacancyDetailsUi, isFavorite: Boolean?) {
        viewModelScope.launch(Dispatchers.IO) {
            _stateFavoriteFlow.value = VacancyFavoriteState.Loading
            if (isFavorite == true) {
                interactor.remove(vacancy.toJobInfo(vacancyId.toLong()))
            } else {
                interactor.add(vacancy.toJobInfo(vacancyId.toLong()))
            }
            _stateFavoriteFlow.value = VacancyFavoriteState.Success
            observeFavoriteStatus(vacancyId)
        }

    }

    private fun updateState(state: VacancyState) {
        _stateFlow.update {
            state
        }
    }

    private suspend fun doRequest(term: String?) {
        if (term.isNullOrEmpty()) return

        updateState(VacancyState.Loading)

        vacancyDetailsUseCase(term)
            .collect { result ->
                result.vacancy?.let { processResult(it, result.error, result.term) }
            }
    }

    private fun processResult(vacancy: VacancyDetails, error: Int?, term: String) {
        updateState(
            when {
                // error != null -> VacancyState.ConnectionError(error, term)
                else ->
                    VacancyState.ShowDetails(vacancy, term)
            }
        )
    }
}
