package ru.practicum.android.microhh.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.microhh.core.db.AppDataBase

val dataModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDataBase::class.java,
            "database.db"
        ).build()
    }

    /*
    single<RetrofitNetworkClient> {
        RetrofitNetworkClientImpl(get())
    }

    single {
        androidContext().getSharedPreferences(androidContext()
            .getString(R.string.prefs_file_name), Context.MODE_PRIVATE)
    }

    factory { Gson() }*/

}
