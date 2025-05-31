package ru.practicum.android.microhh.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.microhh.favorites.presentation.ui.FavoritesViewModel

val viewModelModule = module {

    /*viewModel {
        MainActivityViewModel(get())
    }*/

    viewModel {
        FavoritesViewModel(get())
    }

}
