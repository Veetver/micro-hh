package ru.practicum.android.microhh.favorites.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.practicum.android.microhh.core.domain.interactors.favorites.FavoriteJobInteractor
import ru.practicum.android.microhh.favorites.presentation.ui.interfaces.FavoriteJobScreenState

class FavoritesViewModel(
    private val favoriteJobInteractor: FavoriteJobInteractor,
) : ViewModel() {
    private val _loadingJobState = MutableStateFlow<FavoriteJobScreenState>(FavoriteJobScreenState.Initial)
    fun getLoadingJobState(): StateFlow<FavoriteJobScreenState> = _loadingJobState

    fun showFavorites() {
        viewModelScope.launch {
            favoriteJobInteractor.findAll()
                .onEach { data ->
                    if (data.isEmpty()) {
                        _loadingJobState.value = FavoriteJobScreenState.Empty
                    } else {
                        _loadingJobState.value = FavoriteJobScreenState.FavoriteContent(data)
                    }
                }
                .onStart { _loadingJobState.value = FavoriteJobScreenState.Loading }
                .catch { error ->
                    _loadingJobState.value = FavoriteJobScreenState.Error(error.message)
                }
                .launchIn(this)
        }
    }

}
