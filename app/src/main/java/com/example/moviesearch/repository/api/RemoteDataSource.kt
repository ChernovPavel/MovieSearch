package com.example.moviesearch.repository.api

import com.example.moviesearch.BuildConfig
import com.example.moviesearch.model.MovieDTO
import com.example.moviesearch.model.MoviesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


private const val RUSSIAN = "ru"
private const val ENGLISH = "en"

@Singleton
class RemoteDataSource @Inject constructor(private val movieApi: MovieAPI) {

    suspend fun getMovieDetails(id: Int): Response<MovieDTO> {
        return movieApi.getMovie(id, BuildConfig.MOVIE_API_KEY)
    }

    fun getListTopMovies(isRuLanguage: Boolean): Flow<MoviesResponse> =
        flow {
            val movieLanguage = if (isRuLanguage) RUSSIAN else ENGLISH
            emit(movieApi.getTopMovies(movieLanguage, BuildConfig.MOVIE_API_KEY))
        }
}