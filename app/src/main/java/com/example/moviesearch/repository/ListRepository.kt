package com.example.moviesearch.repository

import com.example.moviesearch.model.MoviesResponse
import retrofit2.Response

interface ListRepository {
    suspend fun getTopMoviesFromServer(isRuLanguage: Boolean): Response<MoviesResponse>
}