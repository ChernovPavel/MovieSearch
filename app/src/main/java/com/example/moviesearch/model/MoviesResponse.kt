package com.example.moviesearch.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<MovieDTO>
) : Parcelable
