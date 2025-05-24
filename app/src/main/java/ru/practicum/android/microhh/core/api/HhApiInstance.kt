package ru.practicum.android.microhh.core.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HhApiInstance {
    private const val baserUrl = "https://api.hh.ru"

    private val gson: Gson = GsonBuilder()
        .serializeNulls()
        .create()

    val HHService: HhApi by lazy {
        Retrofit.Builder()
            .baseUrl(baserUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(HhApi::class.java)
    }

}
