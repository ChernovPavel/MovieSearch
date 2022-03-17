package com.example.moviesearch.utils

import com.example.moviesearch.model.Movie
import com.example.moviesearch.model.MovieDTO
import com.example.moviesearch.model.MoviesResponse

fun convertDtoToModel(movieDTO: MovieDTO): List<Movie> {
    return listOf(
        Movie(
            movieDTO.id!!,
            movieDTO.title!!,
            movieDTO.overview!!,
            movieDTO.genres?.get(0)?.name!!,
            movieDTO.release_date!!,
            movieDTO.poster_path
        )
    )
}

fun convertMoviesResponseToModel(listMovies: MoviesResponse): List<Movie> {
    val list: List<Movie> = listMovies.results.map {
        Movie(it.id!!, it.title!!, it.overview!!, it.release_date!!, "", it.poster_path!!)
    }
    return list
}
