package com.example.moviesearch.repository

import com.example.moviesearch.model.MoviesResponse

interface MainRepo {
    fun getTopMoviesFromServer(callback: retrofit2.Callback<MoviesResponse>)
}