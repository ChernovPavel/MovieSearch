package com.example.moviesearch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val genre: String,
    val poster_path: String?,
//    val vote_average: Double?
) : Parcelable

fun getMovies() = listOf(
    Movie(129, "Унесённые призраками", "Movie about...", "Фантастика", "02.11.1997", "/5tHJGgjCf96Uli8v12VdwCkYq1g.jpg"),
    Movie(496243, "Паразиты", "Movie about...", "Драма", "03.11.1999", "/mSN0DRhFhHNoBZBdhH6N0bx4DfC.jpg"),
    Movie(278, "Побег из Шоушенка", "Movie about...", "тест", "24.02.2022", "/yvmKPlTIi0xdcFQIFcQKQJcI63W.jpg"),
    Movie(244786, "Одержимость", "Movie about...", "Биография", "12.11.1993", "/w7T54XGPKawDli7IcxnTEtQqvSp.jpg"),
    Movie(13, "Форрест Гамп", "Movie about...", "Комедия", "23.11.1994", "/oTiRsQCIHJPUR7wA1chpTf8j9go.jpg"),
    Movie(4935, "Ходячий замок", "Movie about...", "Драма", "24.11.2011", "/sCn4HFkT6HJYiCbQRS8QsRmaUDT.jpg"),
    Movie(680, "Криминальное чтиво", "Movie about...", "Криминал", "24.09.1994", "/tEfPt3FQ1on0DLRM3QOlliLc9Lk.jpg")
)
