package ru.practicum.android.microhh.di

import org.koin.dsl.module
import ru.practicum.android.microhh.core.db.impl.VacancyDetailsDBConvertorImpl
import ru.practicum.android.microhh.country.data.impl.CountryRepositoryImpl
import ru.practicum.android.microhh.country.domain.api.CountryRepository
import ru.practicum.android.microhh.favorites.data.impl.FavoriteVacancyRepositoryImpl
import ru.practicum.android.microhh.favorites.domain.api.FavoriteVacancyRepository
import ru.practicum.android.microhh.filters.data.impl.SettingsRepositoryImpl
import ru.practicum.android.microhh.filters.domain.api.SettingsRepository
import ru.practicum.android.microhh.industry.data.impl.IndustryRepositoryImpl
import ru.practicum.android.microhh.industry.domain.api.IndustryRepository
import ru.practicum.android.microhh.region.data.impl.RegionRepositoryImpl
import ru.practicum.android.microhh.region.domain.api.RegionRepository
import ru.practicum.android.microhh.search.data.impl.VacanciesSearchRepositoryImpl
import ru.practicum.android.microhh.search.domain.api.VacanciesSearchRepository
import ru.practicum.android.microhh.vacancy.data.impl.VacancyDetailsRepositoryImpl
import ru.practicum.android.microhh.vacancy.domain.api.VacancyDetailsRepository


val repositoryModule = module {

    single<VacanciesSearchRepository> {
        VacanciesSearchRepositoryImpl(get(), get())
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

    single<IndustryRepository> {
        IndustryRepositoryImpl(get())
    }

    single<CountryRepository> {
        CountryRepositoryImpl(
            networkClient = get()
        )
    }

    single<RegionRepository> {
        RegionRepositoryImpl(
            networkClient = get()
        )
    }
}
