package com.example.moviesearch.repository

import com.example.moviesearch.model.MoviesResponse
import com.example.moviesearch.model.getMovies
import retrofit2.Callback

class MainRepositoryImpl(private val remoteDataSource: RemoteDataSource) : MainRepo {

    override fun getMovieFromLocalStorage() = getMovies()

    override fun getTopMoviesFromServer(callback: Callback<MoviesResponse>) {
        remoteDataSource.getListTopMovies(callback)
    }
}