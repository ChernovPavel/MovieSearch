package com.example.moviesearch.room

import androidx.room.*

@Dao
interface HistoryDao {
    @Query("SELECT * FROM HistoryEntity")
    fun all(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE title LIKE :title")
    fun getDataByWord(title: String): List<HistoryEntity>

    @Query("SELECT note FROM NoteEntity WHERE IdMovie = :movieId")
    fun getNote(movieId: Int): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: HistoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(entity: NoteEntity)

    @Update
    fun update(entity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)
}