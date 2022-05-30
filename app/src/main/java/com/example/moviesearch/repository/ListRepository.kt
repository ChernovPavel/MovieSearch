package com.example.moviesearch.repository

import com.example.moviesearch.di.ListScope
import com.example.moviesearch.repository.api.RemoteDataSource
import com.example.moviesearch.utils.CORRUPTED_DATA
import com.example.moviesearch.utils.SERVER_ERROR
import com.example.moviesearch.utils.convertMoviesResponseToMovie
import com.example.moviesearch.viewmodel.AppState
import java.io.IOException
import javax.inject.Inject

@ListScope
class ListRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ListRepository {

    override suspend fun getTopMoviesFromServer(isRuLanguage: Boolean): AppState {

        var appState: AppState = AppState.Loading
        try {
            val response = remoteDataSource.getListTopMovies(isRuLanguage)

            if (response.isSuccessful) {
                response.body()?.let {
                    appState = if (it.results.isEmpty()) {
                        AppState.Error(Throwable(CORRUPTED_DATA))
                    } else {
                        AppState.Success(convertMoviesResponseToMovie(it))
                    }
                }
            } else {
                appState = AppState.Error(Throwable(SERVER_ERROR))
            }
        } catch (exp: IOException) {
            appState = AppState.Error(Throwable(SERVER_ERROR))
        }
        return appState
    }
}

interface ListRepository {
    suspend fun getTopMoviesFromServer(isRuLanguage: Boolean): AppState
}
