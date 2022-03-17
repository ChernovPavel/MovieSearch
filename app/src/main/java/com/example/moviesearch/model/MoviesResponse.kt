package com.example.moviesearch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesResponse(
    val page: Int,
    val results: List<MovieDTO>
) : Parcelable
