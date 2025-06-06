package ru.practicum.android.microhh.di

import org.koin.dsl.module
import ru.practicum.android.microhh.core.db.impl.VacancyDetailsDBConvertorImpl
import ru.practicum.android.microhh.favorites.data.impl.FavoriteVacancyRepositoryImpl
import ru.practicum.android.microhh.favorites.domain.api.FavoriteVacancyRepository
import ru.practicum.android.microhh.filters.data.impl.SettingsRepositoryImpl
import ru.practicum.android.microhh.filters.domain.api.SettingsRepository
import ru.practicum.android.microhh.search.data.impl.VacancySearchRepositoryImpl
import ru.practicum.android.microhh.search.domain.api.VacancySearchRepository
import ru.practicum.android.microhh.vacancy.data.impl.VacancyDetailsRepositoryImpl
import ru.practicum.android.microhh.vacancy.domain.api.VacancyDetailsRepository


val repositoryModule = module {

    single<VacancySearchRepository> {
        VacancySearchRepositoryImpl(get(), get())
    }

    single { VacancyDetailsDBConvertorImpl() }

    single<FavoriteVacancyRepository> {
        FavoriteVacancyRepositoryImpl(get(), get())
    }

    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get(), get())
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(get(), get())
    }
}
