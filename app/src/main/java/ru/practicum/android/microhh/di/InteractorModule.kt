package ru.practicum.android.microhh.di

import org.koin.dsl.module
import ru.practicum.android.microhh.core.domain.interactors.favorites.FavoriteJobInteractor
import ru.practicum.android.microhh.core.domain.interactors.favorites.FavoriteJobInteractorImpl

val interactorModule = module {

    /*single<MainActivityInteractor> {
        MainActivityInteractorImpl(get())
    }*/

    factory<FavoriteJobInteractor> {
        FavoriteJobInteractorImpl(get())
    }

}
