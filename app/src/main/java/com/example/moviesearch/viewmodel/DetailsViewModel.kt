package com.example.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.model.Movie
import com.example.moviesearch.model.MovieDTO
import com.example.moviesearch.repository.DetailsRepository
import com.example.moviesearch.repository.DetailsRepositoryImpl
import com.example.moviesearch.repository.RemoteDataSource
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

private const val SERVER_ERROR = "Ошибка сервера"
private const val CORRUPTED_DATA = "Неполные данные"

class DetailsViewModel : ViewModel() {
    private val detailsLiveData: MutableLiveData<AppState> = MutableLiveData()
    private val detailsRepositoryImpl: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource())

    fun getLiveData() = detailsLiveData

    fun getMovieFromRemoteSource(requestLink: String) {
        detailsLiveData.value = AppState.Loading
        detailsRepositoryImpl.getMovieDetailsFromServer(requestLink, callback)
    }

    private val callback = object : Callback {
        override fun onFailure(call: Call?, response: IOException) {

        }

        override fun onResponse(call: Call?, response: Response) {
            val serverResponse: String? = response.body()?.string()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }
    }

    private fun checkResponse(serverResponse: String): AppState {
        val movieDTO: MovieDTO = Gson().fromJson(serverResponse, MovieDTO::class.java)
        return if (movieDTO.title == null ||
            movieDTO.overview == null ||
            movieDTO.release_date == null ||
            movieDTO.genres?.get(0)?.name == null
        ) {
            AppState.Error(Throwable(CORRUPTED_DATA))
        } else {
            AppState.Success(convertDtoToModel(movieDTO))
        }
    }

    private fun convertDtoToModel(movieDTO: MovieDTO): List<Movie> {
        return listOf(
            Movie(
                movieDTO.id!!,
                movieDTO.title!!,
                movieDTO.overview!!,
                movieDTO.genres?.get(0)?.name!!,
                movieDTO.release_date!!
            )
        )
    }
}