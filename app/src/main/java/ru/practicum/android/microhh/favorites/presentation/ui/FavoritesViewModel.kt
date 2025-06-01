package ru.practicum.android.microhh.favorites.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.practicum.android.microhh.core.domain.interactors.favorites.FavoriteJobInteractor
import ru.practicum.android.microhh.favorites.presentation.ui.interfaces.FavoriteJobScreenState

class FavoritesViewModel(
    private val favoriteJobInteractor: FavoriteJobInteractor,
) : ViewModel() {
    private val _loadingJobState = MutableStateFlow<FavoriteJobScreenState>(FavoriteJobScreenState.Initial)
    fun getLoadingJobState(): StateFlow<FavoriteJobScreenState> = _loadingJobState

    init {
        showFavorites()
    }

    private fun showFavorites() {
        viewModelScope.launch {
            favoriteJobInteractor.findAll()
                .map { data ->
                    if (data.isEmpty()) {
                        FavoriteJobScreenState.Empty
                    } else {
                        FavoriteJobScreenState.FavoriteContent(data)
                    }
                }
                .onStart { emit(FavoriteJobScreenState.Loading) }
                .catch { error ->
                    emit(FavoriteJobScreenState.Error(error.message))
                }
                .launchIn(this)
        }
    }

}
