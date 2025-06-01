package ru.practicum.android.microhh.di

import org.koin.dsl.module
import ru.practicum.android.microhh.core.data.repositories.FavoriteJobRepositoryImpl
import ru.practicum.android.microhh.core.db.JobInfoDBConvertor
import ru.practicum.android.microhh.core.domain.repositories.favorites.FavoriteJobRepository

val repositoryModule = module {

    /*single<MainActivityRepository> {
        MainActivityRepositoryImpl(get())
    }*/

    single { JobInfoDBConvertor() }

    single<FavoriteJobRepository> {
        FavoriteJobRepositoryImpl(get(), get())
    }
}
