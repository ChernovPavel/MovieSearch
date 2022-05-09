package com.example.moviesearch.repository

import com.example.moviesearch.model.MovieDTO
import retrofit2.Response

interface DetailsRepository {
    suspend fun getMovieDetailsFromServer(id: Int): Response<MovieDTO>
}