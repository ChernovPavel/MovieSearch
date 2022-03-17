package com.example.moviesearch.repository

import com.example.moviesearch.model.Movie
import com.example.moviesearch.model.MoviesResponse

interface MainRepo {
    fun getMovieFromLocalStorage(): List<Movie>
    fun getTopMoviesFromServer(callback: retrofit2.Callback<MoviesResponse>)
}