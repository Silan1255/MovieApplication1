package com.example.movieapplication1.data.model

data class Movie(
    val dates: Dates? = null,
    val page: Int? = null,
    val results: ArrayList<Result>? = null,
    val total_pages: Int? = null,
    val total_results: Int? = null
)