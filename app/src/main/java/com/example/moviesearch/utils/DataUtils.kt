package com.example.moviesearch.utils

import com.example.moviesearch.model.Movie
import com.example.moviesearch.model.MovieDTO
import com.example.moviesearch.repository.response.ListMoviesResponse
import com.example.moviesearch.room.HistoryEntity

fun convertDtoToModel(movieDTO: MovieDTO): List<Movie> {
    return listOf(
        Movie(
            movieDTO.id!!,
            movieDTO.title!!,
            movieDTO.overview!!,
            movieDTO.releaseDate!!,
            movieDTO.genres?.get(0)?.name!!,
            movieDTO.backdropPath,
            movieDTO.posterPath,
            movieDTO.voteAverage
        )
    )
}

fun convertMoviesResponseToMovie(listMovies: ListMoviesResponse): List<Movie> {
    val list: List<Movie> = listMovies.results.map {
        Movie(
            it.id!!,
            it.title!!,
            it.overview!!,
            it.releaseDate!!,
            "",
            it.backdropPath!!,
            it.posterPath!!,
            it.voteAverage
        )
    }
    return list
}

fun convertHistoryEntityToMovie(entityList: List<HistoryEntity>): List<Movie> {
    return entityList.map {
        Movie(
            it.idMovie,
            it.title,
            it.overview,
            it.release_date,
            it.genre,
            it.backdrop_path,
            it.poster_path,
            it.vote_average
        )
    }
}

fun convertMovieToEntity(movie: Movie): HistoryEntity {
    return HistoryEntity(
        0,
        movie.id,
        movie.title,
        movie.overview,
        movie.release_date,
        movie.genre,
        movie.backdrop_path!!,
        movie.poster_path!!,
        movie.vote_average!!
    )
}
