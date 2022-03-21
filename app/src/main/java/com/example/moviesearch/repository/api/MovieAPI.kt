package com.example.moviesearch.repository.api

import com.example.moviesearch.model.MovieDTO
import com.example.moviesearch.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {
    @GET("3/movie/{id}?language=ru")
    fun getMovie(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieDTO>

    @GET("3/movie/top_rated?language=ru&page=1&region=ru")
    fun getTopMovies(
        @Query("api_key") apiKey: String
    ): Call<MoviesResponse>
}