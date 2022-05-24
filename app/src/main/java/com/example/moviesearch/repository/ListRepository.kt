package com.example.moviesearch.repository

import com.example.moviesearch.model.MovieDTO
import kotlinx.coroutines.flow.Flow

interface ListRepository {
    fun getTopMoviesFromServer(isRuLanguage: Boolean): Flow<List<MovieDTO>>
}