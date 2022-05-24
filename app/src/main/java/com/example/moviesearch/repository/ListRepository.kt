package com.example.moviesearch.repository

import com.example.moviesearch.di.ListScope
import com.example.moviesearch.model.MovieDTO
import com.example.moviesearch.repository.api.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ListScope
class ListRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ListRepository {
    override fun getTopMoviesFromServer(isRuLanguage: Boolean): Flow<List<MovieDTO>> =
        flow { remoteDataSource.getListTopMovies(isRuLanguage).collect { emit(it.results) } }

}

interface ListRepository {
    fun getTopMoviesFromServer(isRuLanguage: Boolean): Flow<List<MovieDTO>>
}
