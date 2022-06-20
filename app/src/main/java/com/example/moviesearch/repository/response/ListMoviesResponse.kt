package com.example.moviesearch.repository.response

import android.os.Parcelable
import com.example.moviesearch.model.MovieDTO
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListMoviesResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<MovieDTO>
) : Parcelable
