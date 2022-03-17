package com.example.moviesearch.repository

import com.example.moviesearch.model.MoviesResponse
import retrofit2.Callback

class MainRepositoryImpl(private val remoteDataSource: RemoteDataSource) : MainRepo {

    override fun getTopMoviesFromServer(callback: Callback<MoviesResponse>) {
        remoteDataSource.getListTopMovies(callback)
    }
}