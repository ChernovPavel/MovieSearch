package com.example.moviesearch.repository

import com.example.moviesearch.model.Movie

interface LocalRepository {
    suspend fun getAllHistory(): List<Movie>
    suspend fun saveEntity(movie: Movie)
    suspend fun saveNote(note: String, movieId: Int)
    suspend fun getNote(movieId: Int): String
}