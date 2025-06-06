package ru.practicum.android.microhh.di

import org.koin.dsl.module
import ru.practicum.android.microhh.favorites.domain.api.FavoriteVacancyInteractor
import ru.practicum.android.microhh.favorites.domain.impl.FavoriteVacancyInteractorImpl

val interactorModule = module {

    factory<FavoriteVacancyInteractor> {
        FavoriteVacancyInteractorImpl(get())
    }

}
