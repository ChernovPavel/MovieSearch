package com.example.moviesearch.repository

import com.example.moviesearch.BuildConfig
import com.example.moviesearch.model.MovieDTO
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    private val movieApi = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build().create(MovieAPI::class.java)

    fun getMovieDetails(id: Int, callback: Callback<MovieDTO>) {
        movieApi.getMovie(id, BuildConfig.MOVIE_API_KEY).enqueue(callback)
    }
}