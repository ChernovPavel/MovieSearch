package com.example.moviesearch.repository

import com.example.moviesearch.model.MoviesResponse

interface MainRepository {
    fun getTopMoviesFromServer(isRuLanguage: Boolean, callback: retrofit2.Callback<MoviesResponse>)
}