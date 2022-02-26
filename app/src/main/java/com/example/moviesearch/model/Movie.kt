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
    Movie(1,"Star Wars", "Movie about...", "Фантастика", "02.11.1997"),
    Movie(2,"Зеленая миля", "Movie about...", "Драма", "03.11.1999"),
    Movie(278,"Побег из Шоушенка", "Movie about...", "тест", "24.02.2022"),
    Movie(4,"Список Шиндлера", "Movie about...", "Биография", "12.11.1993"),
    Movie(5,"Форрест Гамп", "Movie about...", "Комедия", "23.11.1994"),
    Movie(6,"1+1", "Movie about...", "Комедия", "24.11.2011"),
    Movie(7,"Криминальное чтиво", "Movie about...", "Криминал", "24.09.1994")
)
