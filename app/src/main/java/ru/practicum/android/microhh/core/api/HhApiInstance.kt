package ru.practicum.android.microhh.core.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.microhh.BuildConfig

object HhApiInstance {
    private const val baserUrl = "https://api.hh.ru"

    private val gson: Gson = GsonBuilder()
        .serializeNulls()
        .create()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain: Interceptor.Chain ->
            val originalRequest: Request = chain.request()
            val requestWithHeaders = originalRequest.newBuilder()
                .header("User-Agent", BuildConfig.APPLICATION_ID)
                .header("Accept", "application/json")
                .build()
            chain.proceed(requestWithHeaders)
        }
        .build()

    val HHService: HhApi by lazy {
        Retrofit.Builder()
            .baseUrl(baserUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(HhApi::class.java)
    }

}
