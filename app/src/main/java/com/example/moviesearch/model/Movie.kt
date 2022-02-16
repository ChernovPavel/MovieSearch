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
        Movie("Star Wars 1", "Movie about...", "fantasy", "02.11.1997" ),
        Movie("Star Wars 2", "Movie about...", "fantasy", "03.11.1998" ),
        Movie("Star Wars 3", "Movie about...", "fantasy", "04.10.1999" ),
        Movie("Star Wars 4", "Movie about...", "fantasy", "12.11.2000" ),
        Movie("Star Wars 5", "Movie about...", "fantasy", "23.11.2001" ),
        Movie("Star Wars 6", "Movie about...", "fantasy", "24.11.2002" )
    )
}
