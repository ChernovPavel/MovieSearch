package com.example.moviesearch.repository

import com.example.moviesearch.di.DetailsScope
import com.example.moviesearch.model.MovieDTO
import com.example.moviesearch.repository.api.RemoteDataSource
import retrofit2.Response
import javax.inject.Inject

@DetailsScope
class DetailsRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    DetailsRepository {

    override suspend fun getMovieDetailsFromServer(id: Int): Response<MovieDTO> {
        return remoteDataSource.getMovieDetails(id)
    }
}

interface DetailsRepository {
    suspend fun getMovieDetailsFromServer(id: Int): Response<MovieDTO>
}