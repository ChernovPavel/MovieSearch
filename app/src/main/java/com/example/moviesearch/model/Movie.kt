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
    val backdrop_path: String?,
    val poster_path: String?,
    val vote_average: Double?
) : Parcelable
