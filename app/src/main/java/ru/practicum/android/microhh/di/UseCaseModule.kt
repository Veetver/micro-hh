package ru.practicum.android.microhh.di

import org.koin.dsl.module
import ru.practicum.android.microhh.search.domain.impl.VacancySearchUseCase

val useCaseModule = module {

    single<VacancySearchUseCase> {
        VacancySearchUseCase(get())
    }
}
