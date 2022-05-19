package com.example.moviesearch.repository

import com.example.moviesearch.di.ListScope
import com.example.moviesearch.model.MoviesResponse
import com.example.moviesearch.repository.api.RemoteDataSource
import retrofit2.Response
import javax.inject.Inject

@ListScope
class ListRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    ListRepository {

    override suspend fun getTopMoviesFromServer(isRuLanguage: Boolean): Response<MoviesResponse> {
        return remoteDataSource.getListTopMovies(isRuLanguage)
    }
}