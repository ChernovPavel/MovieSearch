package com.example.moviesearch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val name: String = "Titanic",
    val description: String = "Movie about the sinking of the titanic",
    val genre: String = "drama",
    val release_date: String = "01.11.1997"
) : Parcelable

fun getMovies() : List<Movie> {
    return listOf(
        Movie("Star Wars", "Movie about...", "Фантастика", "02.11.1997" ),
        Movie("Зеленая миля", "Movie about...", "Драма", "03.11.1999" ),
        Movie("Побег из Шоушенка", "Movie about...", "Драма", "04.10.1994" ),
        Movie("Список Шиндлера", "Movie about...", "Биография", "12.11.1993" ),
        Movie("Форрест Гамп", "Movie about...", "Комедия", "23.11.1994" ),
        Movie("1+1", "Movie about...", "Комедия", "24.11.2011" ),
        Movie("Криминальное чтиво", "Movie about...", "Криминал", "24.09.1994" )
    )
}
