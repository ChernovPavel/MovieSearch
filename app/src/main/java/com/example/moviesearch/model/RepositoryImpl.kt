package com.example.moviesearch.model

class RepositoryImpl : Repo{
    override fun getMovieFromServer(): Movie {
        return Movie()
    }

    override fun getMovieFromLocalStorage(): Movie {
        return Movie()
    }
}