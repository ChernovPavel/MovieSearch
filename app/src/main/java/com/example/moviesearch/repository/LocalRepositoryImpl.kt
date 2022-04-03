package com.example.moviesearch.repository

import com.example.moviesearch.model.Movie
import com.example.moviesearch.room.HistoryDao
import com.example.moviesearch.room.NoteEntity
import com.example.moviesearch.utils.convertHistoryEntityToMovie
import com.example.moviesearch.utils.convertMovieToEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDao): LocalRepository {
    override fun getAllHistory(): List<Movie> {
        return convertHistoryEntityToMovie(localDataSource.all())
    }

    override fun saveEntity(movie: Movie) {
        localDataSource.insert(convertMovieToEntity(movie))
    }

    override fun saveNote(note: String, movieId: Int) {
        localDataSource.insertNote(NoteEntity(movieId, note))
    }

    override fun getNote(movieId: Int): String {
        return localDataSource.getNote(movieId)
    }
}