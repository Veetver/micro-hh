package ru.practicum.android.microhh.di

import org.koin.dsl.module
import ru.practicum.android.microhh.core.data.repositories.FavoriteJobRepositoryImpl
import ru.practicum.android.microhh.core.domain.repositories.favorites.FavoriteJobRepository
import ru.practicum.android.microhh.search.data.impl.VacancySearchRepositoryImpl
import ru.practicum.android.microhh.search.domain.api.VacancySearchRepository

val repositoryModule = module {

    single<VacancySearchRepository> {
        VacancySearchRepositoryImpl(get(), get())
    }

    factory<FavoriteJobRepository> {
        FavoriteJobRepositoryImpl(get(), get())
    }
}
