package com.example.moviesearch.model

class RepositoryImpl : Repo {
    override fun getMovieFromServer(): Movie {
        return Movie(1, "Титаник", "About", "drama", "01.01.2001")
    }

    override fun getMovieFromLocalStorage() = getMovies()
}