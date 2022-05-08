package com.example.moviesearch.repository

import com.example.moviesearch.model.MoviesResponse
import com.example.moviesearch.repository.api.RemoteDataSource
import retrofit2.Callback

class ListRepositoryImpl(private val remoteDataSource: RemoteDataSource) : ListRepository {

    override fun getTopMoviesFromServer(isRuLanguage: Boolean, callback: Callback<MoviesResponse>) {
        remoteDataSource.getListTopMovies(isRuLanguage, callback)
    }
}