package com.example.moviesearch.repository.api

import com.example.moviesearch.model.MovieDTO
import com.example.moviesearch.model.MoviesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {
    @GET("3/movie/{id}?language=ru")
    suspend fun getMovie(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieDTO>

    @GET("3/movie/top_rated?page=1&region=ru")
    fun getTopMovies(
        @Query("language") language: String,
        @Query("api_key") apiKey: String
    ): Call<MoviesResponse>
}