package com.example.moviesearch.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(HistoryEntity::class, NoteEntity::class), version = 3, exportSchema = false)
abstract class HistoryDataBase: RoomDatabase() {

    abstract fun historyDao() : HistoryDao
}