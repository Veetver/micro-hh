package ru.practicum.android.microhh.vacancy.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.microhh.core.models.items.Vacancy

class VacancyViewModel(private val vacancyInteractor: VacancyInteractor) : ViewModel() {

    private val _vacancy= MutableLiveData<Vacancy>()
    val vacancy: LiveData<Vacancy> = _vacancy

    fun getPlaylistById(playlistId: Int) {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

}
