package ru.practicum.android.microhh.core.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HhApiInstance {
    private const val baserUrl = "https://api.hh.ru"

    val HHService: HhApi by lazy {
        Retrofit.Builder()
            .baseUrl(baserUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HhApi::class.java)
    }

}
