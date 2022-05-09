package com.example.moviesearch.repository

import com.example.moviesearch.model.MovieDTO
import com.example.moviesearch.repository.api.RemoteDataSource
import retrofit2.Response

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {

    override suspend fun getMovieDetailsFromServer(id: Int): Response<MovieDTO> {
        return remoteDataSource.getMovieDetails(id)
    }
}