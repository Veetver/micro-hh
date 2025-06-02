package ru.practicum.android.microhh.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.microhh.favorites.presentation.ui.FavoritesViewModel
import ru.practicum.android.microhh.search.presentation.SearchViewModel

val viewModelModule = module {

    viewModel {
        SearchViewModel(get())
    }

    viewModel {
        FavoritesViewModel(get())
    }

}
