package com.example.moviesearch.repository

import com.example.moviesearch.model.MovieDTO

interface DetailsRepository {
    fun getMovieDetailsFromServer(id: Int, callback: retrofit2.Callback<MovieDTO>)
}