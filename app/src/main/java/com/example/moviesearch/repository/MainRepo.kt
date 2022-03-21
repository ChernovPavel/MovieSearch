package com.example.moviesearch.repository

import com.example.moviesearch.model.Movie

interface MainRepo {
    fun getMovieFromServer(): Movie
    fun getMovieFromLocalStorage(): List<Movie>
}