package com.example.moviesearch.repository

import com.example.moviesearch.model.Movie

interface LocalRepository {
    fun getAllHistory(): List<Movie>
    fun saveEntity(movie: Movie)
    fun saveNote(note: String, movieId: Int)
    fun getNote(movieId: Int): String
}