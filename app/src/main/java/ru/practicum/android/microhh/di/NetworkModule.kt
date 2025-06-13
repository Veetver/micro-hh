package ru.practicum.android.microhh.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.microhh.BuildConfig
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.country.data.network.HhCountryService
import ru.practicum.android.microhh.industry.data.network.HhIndustryService
import ru.practicum.android.microhh.region.data.network.HhRegionService
import ru.practicum.android.microhh.search.data.network.HhVacanciesService
import ru.practicum.android.microhh.vacancy.data.network.HhVacancyService

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

    single<HhVacanciesService> {
        Retrofit.Builder()
            .baseUrl(androidContext().getString(R.string.hh_base_url))
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .build()
            .create(HhVacanciesService::class.java)
    }

    single<HhVacancyService> {
        Retrofit.Builder()
            .baseUrl(androidContext().getString(R.string.hh_base_url))
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .build()
            .create(HhVacancyService::class.java)
    }

    single<HhCountryService> {
        Retrofit.Builder()
            .baseUrl(androidContext().getString(R.string.hh_base_url))
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .build()
            .create(HhCountryService::class.java)
    }

    single<HhRegionService> {
        Retrofit.Builder()
            .baseUrl(androidContext().getString(R.string.hh_base_url))
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .build()
            .create(HhRegionService::class.java)
    }

    single<HhIndustryService> {
        Retrofit.Builder()
            .baseUrl(androidContext().getString(R.string.hh_base_url))
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .build()
            .create(HhIndustryService::class.java)
    }
}
