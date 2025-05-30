package ru.practicum.android.microhh.di

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.Interceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.dsl.module
import ru.practicum.android.microhh.BuildConfig
import ru.practicum.android.microhh.core.api.HhApi

val networkModule = module {
    single {
        GsonBuilder()
            .serializeNulls()
            .create()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("User-Agent", BuildConfig.APPLICATION_ID)
                    .header("Accept", "application/json")
                    .build()
                chain.proceed(request)
            })
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru")
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .build()
    }

    single<HhApi> {
        get<Retrofit>().create(HhApi::class.java)
    }
}
