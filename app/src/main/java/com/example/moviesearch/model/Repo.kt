package com.example.moviesearch.model

interface Repo {
    fun getMovieFromServer(): Movie
    fun getMovieFromLocalStorage(): Movie
}