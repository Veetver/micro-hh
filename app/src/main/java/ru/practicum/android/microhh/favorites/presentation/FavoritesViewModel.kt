package ru.practicum.android.microhh.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.practicum.android.microhh.core.resources.FavoriteVacancyScreenState
import ru.practicum.android.microhh.favorites.domain.api.FavoriteVacancyInteractor

class FavoritesViewModel(
    private val favoriteJobInteractor: FavoriteVacancyInteractor,
) : ViewModel() {

    private val _loadingJobState = MutableStateFlow<FavoriteVacancyScreenState>(FavoriteVacancyScreenState.Initial)
    fun getLoadingJobState(): StateFlow<FavoriteVacancyScreenState> = _loadingJobState

    fun showFavorites() {
        viewModelScope.launch {
            favoriteJobInteractor.findAll()
                .onEach { data ->
                    if (data.isEmpty()) {
                        _loadingJobState.value = FavoriteVacancyScreenState.Empty
                    } else {
                        _loadingJobState.value = FavoriteVacancyScreenState.FavoriteContent(data)
                    }
                }
                .onStart { _loadingJobState.value = FavoriteVacancyScreenState.Loading }
                .catch { error ->
                    _loadingJobState.value = FavoriteVacancyScreenState.Error(error.message)
                }
                .launchIn(this)
        }
    }

}
