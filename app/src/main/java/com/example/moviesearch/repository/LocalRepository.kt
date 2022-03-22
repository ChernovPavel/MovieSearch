package com.example.moviesearch.repository

import com.example.moviesearch.model.Movie

interface LocalRepository {
    fun getAllHistory(): List<Movie>
    fun saveEntity(movie: Movie)
}