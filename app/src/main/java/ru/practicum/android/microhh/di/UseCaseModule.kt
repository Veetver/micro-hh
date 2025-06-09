package ru.practicum.android.microhh.di

import org.koin.dsl.module
import ru.practicum.android.microhh.country.domain.impl.GetCountriesUseCase
import ru.practicum.android.microhh.industry.domain.impl.IndustryListUseCase
import ru.practicum.android.microhh.region.domain.impl.GetRegionByIdUseCase
import ru.practicum.android.microhh.region.domain.impl.GetRegionsWOCountriesUseCase
import ru.practicum.android.microhh.search.domain.impl.VacancySearchUseCase
import ru.practicum.android.microhh.vacancy.domain.impl.VacancyDetailsUseCase

val useCaseModule = module {

    single<VacancySearchUseCase> {
        VacancySearchUseCase(get())
    }

    single<VacancyDetailsUseCase> {
        VacancyDetailsUseCase(get(), get())
    }

    single<IndustryListUseCase> {
        IndustryListUseCase(get())
    }

    single<GetRegionByIdUseCase> {
        GetRegionByIdUseCase(
            repository = get()
        )
    }

    single<GetRegionsWOCountriesUseCase> {
        GetRegionsWOCountriesUseCase(
            repository = get()
        )
    }

    single<GetCountriesUseCase> {
        GetCountriesUseCase(
            repository = get()
        )
    }
}
