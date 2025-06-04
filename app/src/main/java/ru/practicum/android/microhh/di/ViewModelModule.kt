package ru.practicum.android.microhh.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.microhh.favorites.presentation.FavoritesViewModel
import ru.practicum.android.microhh.search.presentation.SearchViewModel
import ru.practicum.android.microhh.vacancy.presentation.ui.VacancyViewModel

val viewModelModule = module {

    viewModel {
        SearchViewModel(get())
    }

    viewModel {
        FavoritesViewModel(get())
    }

    viewModel { (vacancyId: String) ->
        VacancyViewModel(
            vacancyId = vacancyId,
            vacancyDetailsUseCase = get(),
        )
    }
}
