package ru.practicum.android.microhh.vacancy.presentation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.microhh.core.domain.models.VacancyDetails
import ru.practicum.android.microhh.favorites.domain.api.FavoriteVacancyInteractor
import ru.practicum.android.microhh.vacancy.domain.impl.VacancyDetailsUseCase
import ru.practicum.android.microhh.vacancy.presentation.ui.VacancyFavoriteState
import ru.practicum.android.microhh.vacancy.presentation.ui.VacancyState

class VacancyViewModel(
    private val vacancyId: String,
    private val vacancyDetailsUseCase: VacancyDetailsUseCase,
    private val interactor: FavoriteVacancyInteractor
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
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                interactor.isVacancyFavorite(vacancyId.toLong())
                    .collect { isFavorite ->
                        _stateFavoriteFlow.value = if (isFavorite) {
                            VacancyFavoriteState.VacancyFavorite
                        } else {
                            VacancyFavoriteState.VacancyNotFavorite
                        }
                    }
            }.onFailure {
                _stateFavoriteFlow.value = VacancyFavoriteState.Error
            }
        }
    }

    fun updateFavorite(vacancy: VacancyDetails, isFavorite: Boolean?) {
        viewModelScope.launch(Dispatchers.IO) {
            _stateFavoriteFlow.value = VacancyFavoriteState.Loading
            if (isFavorite == true) {
                interactor.remove(vacancy)
            } else {
                interactor.add(vacancy)
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

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
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
                error != null -> VacancyState.ConnectionError(error, term)
                else ->
                    VacancyState.ShowDetails(vacancy, term)
            }
        )
    }
}
