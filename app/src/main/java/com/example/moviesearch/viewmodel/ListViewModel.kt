package com.example.moviesearch.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.di.ListScope
import com.example.moviesearch.model.Movie
import com.example.moviesearch.model.MovieDTO
import com.example.moviesearch.repository.ListRepository
import com.example.moviesearch.utils.IS_RUSSIAN_LANGUAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ListScope
class ListViewModel @Inject constructor(
    listRepository: ListRepository,
    context: Context
) : ViewModel() {

    private val isRuLanguage =
        context.getSharedPreferences("view.MainActivity", Context.MODE_PRIVATE)
            .getBoolean(IS_RUSSIAN_LANGUAGE, false)

    private val _appState = listRepository.getTopMoviesFromServer(isRuLanguage)
        .flowOn(Dispatchers.IO)
        .map { dto -> dto.convert() }
        .map { list -> AppState.Success(list) }
        .onStart<AppState> { emit(AppState.Loading) }
        .catch { ex -> emit(AppState.Error(ex)) }
        .flowOn(Dispatchers.Default)
        .stateIn(viewModelScope, SharingStarted.Eagerly, AppState.Loading)

    val appState: StateFlow<AppState>
        get() = _appState

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