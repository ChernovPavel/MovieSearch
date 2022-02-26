package com.example.moviesearch.model

data class MovieDTO(

    val title: String?,
    val overview: String?,
    val release_date: String?,
    val genres: List<Genres>?
)

data class Genres(

    val id: Int?,
    val name: String?
)