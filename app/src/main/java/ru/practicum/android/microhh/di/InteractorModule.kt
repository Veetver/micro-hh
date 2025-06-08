package ru.practicum.android.microhh.di

import org.koin.dsl.module
import ru.practicum.android.microhh.favorites.domain.api.FavoriteVacancyInteractor
import ru.practicum.android.microhh.favorites.domain.impl.FavoriteVacancyInteractorImpl
import ru.practicum.android.microhh.filters.domain.api.SettingsInteractor
import ru.practicum.android.microhh.filters.domain.impl.SettingsInteractorImpl

val interactorModule = module {

    factory<FavoriteVacancyInteractor> {
        FavoriteVacancyInteractorImpl(get())
    }

    factory<SettingsInteractor> {
        SettingsInteractorImpl(get())
    }
}
