package com.example.moviesearch.repository

import com.example.moviesearch.di.ListScope
import com.example.moviesearch.model.Movie
import com.example.moviesearch.model.MovieDTO
import com.example.moviesearch.repository.api.RemoteDataSource
import com.example.moviesearch.viewmodel.AppState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ListScope
class ListRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ListRepository {

    override fun getTopMoviesFromServer(isRuLanguage: Boolean): Flow<AppState> =

        flow { emit(remoteDataSource.getListTopMovies(isRuLanguage).results) }
            .flowOn(Dispatchers.IO)
            .map { dto -> dto.convert() }
            .map { list -> AppState.Success(list) }
            .onStart<AppState> { emit(AppState.Loading) }
            .catch { ex -> emit(AppState.Error(ex)) }
            .flowOn(Dispatchers.Default)


    private fun List<MovieDTO>.convert() =
        map { item ->
            Movie(
                id = item.id!!,
                title = item.title!!,
                overview = item.overview!!,
                release_date = item.releaseDate!!,
                genre = "",
                backdrop_path = item.backdropPath!!,
                poster_path = item.posterPath!!,
                vote_average = item.voteAverage
            )
        }
}
