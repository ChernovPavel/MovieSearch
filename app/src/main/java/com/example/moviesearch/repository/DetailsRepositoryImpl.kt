package com.example.moviesearch.repository

import com.example.moviesearch.model.MovieDTO

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {

    override fun getMovieDetailsFromServer(id: Int, callback: retrofit2.Callback<MovieDTO>) {
        remoteDataSource.getMovieDetails(id, callback)
    }
}