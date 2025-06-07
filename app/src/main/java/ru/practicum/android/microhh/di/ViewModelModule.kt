package ru.practicum.android.microhh.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.microhh.country.presentation.CountryViewModel
import ru.practicum.android.microhh.favorites.presentation.FavoritesViewModel
import ru.practicum.android.microhh.filters.presentation.FiltersViewModel
import ru.practicum.android.microhh.industry.presentation.IndustryViewModel
import ru.practicum.android.microhh.search.presentation.SearchViewModel
import ru.practicum.android.microhh.vacancy.presentation.VacancyViewModel

val viewModelModule = module {

    viewModel {
        SearchViewModel(get(), get())
    }

    viewModel {
        FavoritesViewModel(get())
    }

    viewModel {
        FiltersViewModel(get())
    }

    viewModel { (vacancyId: String) ->
        VacancyViewModel(
            vacancyId = vacancyId,
            vacancyDetailsUseCase = get(),
            get()
        )
    }

    viewModel {

        CountryViewModel(get())
    }
    
   viewModel {

        IndustryViewModel(get())
    }
}
