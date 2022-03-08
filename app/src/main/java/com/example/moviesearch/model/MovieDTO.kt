package com.example.moviesearch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDTO(
    val id: Int?,
    val title: String?,
    val overview: String?,
    val release_date: String?,
    val genres: List<Genres>?
) : Parcelable

@Parcelize
data class Genres(

    val id: Int?,
    val name: String?
) : Parcelable