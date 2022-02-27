package com.example.moviesearch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int = 1,
    val name: String = "Titanic",
    val overview: String = "Movie about the sinking of the titanic",
    val genre: String = "drama",
    val release_date: String = "01.11.1997"
) : Parcelable

fun getMovies() = listOf(
    Movie(129,"Унесённые призраками", "Movie about...", "Фантастика", "02.11.1997"),
    Movie(496243,"Паразиты", "Movie about...", "Драма", "03.11.1999"),
    Movie(278,"Побег из Шоушенка", "Movie about...", "тест", "24.02.2022"),
    Movie(244786,"Одержимость", "Movie about...", "Биография", "12.11.1993"),
    Movie(13,"Форрест Гамп", "Movie about...", "Комедия", "23.11.1994"),
    Movie(4935,"Ходячий замок", "Movie about...", "Драма", "24.11.2011"),
    Movie(680,"Криминальное чтиво", "Movie about...", "Криминал", "24.09.1994")
)
