package com.example.moviesearch.utils

import com.example.moviesearch.model.Movie
import com.example.moviesearch.model.MovieDTO

fun convertDtoToModel(movieDTO: MovieDTO): List<Movie> {
    return listOf(
        Movie(
            movieDTO.id!!,
            movieDTO.title!!,
            movieDTO.overview!!,
            movieDTO.genres?.get(0)?.name!!,
            movieDTO.release_date!!
        )
    )
}