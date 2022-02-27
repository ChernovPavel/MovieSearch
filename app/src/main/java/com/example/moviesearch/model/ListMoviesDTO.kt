package com.example.moviesearch.model

data class ListMoviesDTO(
    val results: List<MovieItem>?
)

data class MovieItem(
    val id: Int,
    val title: String?,
    val release_date: String?,
    val vote_average: Double?
)