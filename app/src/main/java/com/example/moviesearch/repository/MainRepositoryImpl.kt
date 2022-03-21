package com.example.moviesearch.repository

import com.example.moviesearch.model.Movie
import com.example.moviesearch.model.getMovies

class MainRepositoryImpl : MainRepo {
    override fun getMovieFromServer(): Movie {
        return Movie(1, "Титаник", "About", "drama", "01.01.2001")
    }

    override fun getMovieFromLocalStorage() = getMovies()
}