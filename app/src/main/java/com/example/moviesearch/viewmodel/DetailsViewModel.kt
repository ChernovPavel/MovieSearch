package com.example.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.app.App.Companion.getHistoryDao
import com.example.moviesearch.model.Movie
import com.example.moviesearch.model.MovieDTO
import com.example.moviesearch.repository.DetailsRepository
import com.example.moviesearch.repository.DetailsRepositoryImpl
import com.example.moviesearch.repository.LocalRepository
import com.example.moviesearch.repository.LocalRepositoryImpl
import com.example.moviesearch.repository.api.RemoteDataSource
import com.example.moviesearch.utils.convertDtoToModel

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class DetailsViewModel : ViewModel() {
    val detailsLiveData: MutableLiveData<AppState> = MutableLiveData()
    val noteLiveData: MutableLiveData<String> = MutableLiveData()
    private val detailsRepositoryImpl: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource())
    private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())

    fun getMovieFromAPI(movieId: Int) {
        detailsLiveData.value = AppState.Loading
        detailsRepositoryImpl.getMovieDetailsFromServer(movieId, callback)
    }

    private val callback = object : retrofit2.Callback<MovieDTO> {
        override fun onResponse(
            call: retrofit2.Call<MovieDTO>,
            response: retrofit2.Response<MovieDTO>,
        ) {
            val serverResponse: MovieDTO? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: retrofit2.Call<MovieDTO>, t: Throwable) {
            detailsLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }
    }

    private fun checkResponse(movieDTO: MovieDTO): AppState {
        return if (movieDTO.title == null ||
            movieDTO.overview == null ||
            movieDTO.release_date == null ||
            movieDTO.genres?.get(0)?.name == null ||
            movieDTO.poster_path == null
        ) {
            AppState.Error(Throwable(CORRUPTED_DATA))
        } else {
            AppState.Success(convertDtoToModel(movieDTO))
        }
    }

    fun saveMovieToDB(movie: Movie) = Thread { historyRepository.saveEntity(movie) }.start()

    fun saveNoteToDB(note: String, movieId: Int) = Thread {
        historyRepository.saveNote(note, movieId)
    }.start()

    fun getNoteFromDB(movieId: Int) = Thread {
        noteLiveData.postValue(historyRepository.getNote(movieId))
    }.start()
}