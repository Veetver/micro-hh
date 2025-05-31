package ru.practicum.android.microhh.di

import org.koin.dsl.module
import ru.practicum.android.microhh.core.data.network.RetrofitNetworkClient
import ru.practicum.android.microhh.search.data.impl.RetrofitNetworkClientImpl

val dataModule = module {

    single<RetrofitNetworkClient> {
        RetrofitNetworkClientImpl(get())
    }

    /*single {
        androidContext().getSharedPreferences(androidContext()
            .getString(R.string.prefs_file_name), Context.MODE_PRIVATE)
    }

    factory { Gson() }*/
}
