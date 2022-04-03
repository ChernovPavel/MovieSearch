package com.example.moviesearch.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(

    @PrimaryKey (autoGenerate = false)
    val idMovie: Int,
    val note: String
)