package com.example.moviesearch.repository

import com.example.moviesearch.model.Movie
import com.example.moviesearch.room.HistoryDao
import com.example.moviesearch.room.NoteEntity
import com.example.moviesearch.utils.convertHistoryEntityToMovie
import com.example.moviesearch.utils.convertMovieToEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDao): LocalRepository {
    override suspend fun getAllHistory(): List<Movie> {
        return convertHistoryEntityToMovie(localDataSource.all())
    }

    override suspend fun saveEntity(movie: Movie) {
        localDataSource.insert(convertMovieToEntity(movie))
    }

    override suspend fun saveNote(note: String, movieId: Int) {
        localDataSource.insertNote(NoteEntity(movieId, note))
    }

    override suspend fun getNote(movieId: Int): String {
        return localDataSource.getNote(movieId)
    }
}

interface LocalRepository {
    suspend fun getAllHistory(): List<Movie>
    suspend fun saveEntity(movie: Movie)
    suspend fun saveNote(note: String, movieId: Int)
    suspend fun getNote(movieId: Int): String
}