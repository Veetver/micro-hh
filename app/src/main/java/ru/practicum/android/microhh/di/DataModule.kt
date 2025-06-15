package ru.practicum.android.microhh.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.core.data.dto.VacancyDtoConverter
import ru.practicum.android.microhh.core.data.impl.VacancyDtoConverterImpl
import ru.practicum.android.microhh.core.db.AppDataBase
import ru.practicum.android.microhh.core.db.api.VacancyDetailsDBConvertor
import ru.practicum.android.microhh.core.db.impl.VacancyDetailsDBConvertorImpl
import ru.practicum.android.microhh.core.utils.NetworkCheck
import ru.practicum.android.microhh.core.utils.NetworkErrorHandler
import ru.practicum.android.microhh.country.data.impl.CountryNetworkClientImpl
import ru.practicum.android.microhh.country.data.network.CountryNetworkClient
import ru.practicum.android.microhh.industry.data.impl.IndustryNetworkClientImpl
import ru.practicum.android.microhh.industry.data.network.IndustryNetworkClient
import ru.practicum.android.microhh.region.data.impl.RegionNetworkClientImpl
import ru.practicum.android.microhh.region.data.network.RegionNetworkClient
import ru.practicum.android.microhh.search.data.impl.VacanciesNetworkClientImpl
import ru.practicum.android.microhh.search.data.network.VacanciesNetworkClient
import ru.practicum.android.microhh.vacancy.data.impl.VacancyNetworkClientImpl
import ru.practicum.android.microhh.vacancy.data.network.VacancyNetworkClient

val dataModule = module {

    single { NetworkCheck(get()) }

    single { NetworkErrorHandler(get()) }

    single<VacanciesNetworkClient> {
        VacanciesNetworkClientImpl(get(), get())
    }

    single<VacancyNetworkClient> {
        VacancyNetworkClientImpl(get(), get())
    }

    single<CountryNetworkClient> {
        CountryNetworkClientImpl(get(), get())
    }

    single<RegionNetworkClient> {
        RegionNetworkClientImpl(get(), get())
    }

    single<IndustryNetworkClient> {
        IndustryNetworkClientImpl(get(), get())
    }

    single<VacancyDtoConverter> {
        VacancyDtoConverterImpl(get())
    }

    single<VacancyDetailsDBConvertor> {
        VacancyDetailsDBConvertorImpl()
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDataBase::class.java,
            "database.db"
        ).build()
    }

    single {
        androidContext().getSharedPreferences(
            androidContext().getString(R.string.prefs_file_name),
            Context.MODE_PRIVATE
        )
    }

    factory { Gson() }
}
