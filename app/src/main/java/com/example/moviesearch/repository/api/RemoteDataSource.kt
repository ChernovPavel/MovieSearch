package com.example.moviesearch.repository.api

import com.example.moviesearch.BuildConfig
import com.example.moviesearch.di.RemoteDataScope
import com.example.moviesearch.model.MovieDTO
import com.example.moviesearch.model.MoviesResponse
import retrofit2.Response
import javax.inject.Inject


private const val RUSSIAN = "ru"
private const val ENGLISH = "en"

@RemoteDataScope
class RemoteDataSource @Inject constructor(private val movieApi: MovieAPI) {

    private lateinit var movieLanguage: String

    suspend fun getMovieDetails(id: Int): Response<MovieDTO> {
        return movieApi.getMovie(id, BuildConfig.MOVIE_API_KEY)
    }

    suspend fun getListTopMovies(isRuLanguage: Boolean): Response<MoviesResponse> {
        movieLanguage = if (isRuLanguage) RUSSIAN else ENGLISH
        return movieApi.getTopMovies(movieLanguage, BuildConfig.MOVIE_API_KEY)
    }
}