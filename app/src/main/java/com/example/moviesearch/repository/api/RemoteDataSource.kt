package com.example.moviesearch.repository.api

import com.example.moviesearch.BuildConfig
import com.example.moviesearch.model.MovieDTO
import com.example.moviesearch.model.MoviesResponse
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


private const val RUSSIAN = "ru"
private const val ENGLISH = "en"

class RemoteDataSource @Inject constructor(private val movieApi: MovieAPI) {

    private lateinit var movieLanguage: String

    suspend fun getMovieDetails(id: Int): Response<MovieDTO> {
       return movieApi.getMovie(id, BuildConfig.MOVIE_API_KEY)
    }

    fun getListTopMovies(isRuLanguage: Boolean, callback: Callback<MoviesResponse>) {
        movieLanguage = if (isRuLanguage) RUSSIAN else ENGLISH
        movieApi.getTopMovies(movieLanguage, BuildConfig.MOVIE_API_KEY).enqueue(callback)
    }
}