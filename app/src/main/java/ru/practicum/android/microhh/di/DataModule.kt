package ru.practicum.android.microhh.di

import org.koin.dsl.module

val dataModule = module {

    /*single {
        Room.databaseBuilder(
            androidContext(),
            AppDataBase::class.java,
            androidContext().getString(R.string.database_file_name)
        ).build()
    }

    single<RetrofitNetworkClient> {
        RetrofitNetworkClientImpl(get())
    }

    single {
        androidContext().getSharedPreferences(androidContext()
            .getString(R.string.prefs_file_name), Context.MODE_PRIVATE)
    }

    factory { Gson() }*/

}
