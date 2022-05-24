package com.example.moviesearch.repository

import com.example.moviesearch.viewmodel.AppState
import kotlinx.coroutines.flow.Flow

interface ListRepository {
    fun getTopMoviesFromServer(isRuLanguage: Boolean): Flow<AppState>
}