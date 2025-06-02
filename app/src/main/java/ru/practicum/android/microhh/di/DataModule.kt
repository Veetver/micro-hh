package ru.practicum.android.microhh.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.microhh.core.data.impl.NetworkCheckImpl
import ru.practicum.android.microhh.core.data.network.NetworkCheck
import ru.practicum.android.microhh.core.data.network.RetrofitNetworkClient
import ru.practicum.android.microhh.search.data.dto.VacancyDtoConverter
import ru.practicum.android.microhh.core.db.AppDataBase
import ru.practicum.android.microhh.search.data.impl.RetrofitNetworkClientImpl
import ru.practicum.android.microhh.search.data.impl.VacancyDtoConverterImpl

val dataModule = module {

    single<NetworkCheck> {
        NetworkCheckImpl(get())
    }

    single<RetrofitNetworkClient> {
        RetrofitNetworkClientImpl(get(), get())
    }

    single<VacancyDtoConverter> {
        VacancyDtoConverterImpl(get())
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDataBase::class.java,
            "database.db"
        ).build()
    }

    /*single {
        androidContext().getSharedPreferences(androidContext()
            .getString(R.string.prefs_file_name), Context.MODE_PRIVATE)
    }

    factory { Gson() }*/

}
