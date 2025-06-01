package ru.practicum.android.microhh.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.microhh.BuildConfig
import ru.practicum.android.microhh.core.data.network.HhService

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
                    .header("Authorization", "Bearer ${BuildConfig.HH_ACCESS_TOKEN}")
                    .header("HH-User-Agent", "mirco hh (${BuildConfig.HH_EMAIL})")
                    .header("Accept", "application/json")
                    .build()
                chain.proceed(request)
            }).build()
    }

    single<HhService> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru")
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .build()
            .create(HhService::class.java)
    }
}
