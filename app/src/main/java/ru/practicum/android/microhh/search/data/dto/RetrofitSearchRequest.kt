package ru.practicum.android.microhh.search.data.dto

data class RetrofitSearchRequest(
    val term: String,
    val page: Int = 1,
)
