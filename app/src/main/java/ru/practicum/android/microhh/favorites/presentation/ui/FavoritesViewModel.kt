package ru.practicum.android.microhh.favorites.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.practicum.android.microhh.core.domain.interactors.favorites.FavoriteJobInteractor
import ru.practicum.android.microhh.favorites.presentation.ui.interfaces.FavoriteJobScreenState

class FavoritesViewModel (
    private val favoriteJobInteractor: FavoriteJobInteractor,
): ViewModel() {
    private var loadingJobLiveData = MutableLiveData<FavoriteJobScreenState>()
    fun getLoadingJobLiveData(): LiveData<FavoriteJobScreenState> = loadingJobLiveData

    init {
        showFavorites()
    }


    private fun showFavorites() {
        viewModelScope.launch {
            favoriteJobInteractor.findAll()
                .onEach { data ->
                    loadingJobLiveData.postValue(FavoriteJobScreenState.FavoriteContent(data))
                }
                .launchIn(this)
        }
    }

}
