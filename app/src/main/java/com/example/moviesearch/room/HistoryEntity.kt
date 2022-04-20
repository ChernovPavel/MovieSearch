package com.example.moviesearch.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(

    @PrimaryKey (autoGenerate = true)
    val id: Long,
    val idMovie: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val genre: String,
    val backdrop_path: String,
    val poster_path: String,
    val vote_average: Double
)
