package com.example.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.model.Movie
import com.example.moviesearch.model.MovieDTO
import com.example.moviesearch.repository.DetailsRepository
import com.example.moviesearch.repository.LocalRepository
import com.example.moviesearch.utils.CORRUPTED_DATA
import com.example.moviesearch.utils.SERVER_ERROR
import com.example.moviesearch.utils.convertDtoToModel
import kotlinx.coroutines.launch
import java.io.IOException

class DetailsViewModel(
    private val detailsRepository: DetailsRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    val detailsLiveData: MutableLiveData<AppState> = MutableLiveData()
    val noteLiveData: MutableLiveData<String> = MutableLiveData()

    fun getMovieFromAPI(movieId: Int) {
        detailsLiveData.value = AppState.Loading

        viewModelScope.launch {
            try {
                val responseBody = detailsRepository.getMovieDetailsFromServer(movieId).body()

                responseBody?.let {
                    detailsLiveData.postValue(checkResponse(it))
                }
            } catch (exp: IOException) {
                detailsLiveData.postValue(AppState.Error(Throwable(SERVER_ERROR)))
            }
        }
    }

    private fun checkResponse(movieDTO: MovieDTO): AppState {
        return if (movieDTO.title == null ||
            movieDTO.overview == null ||
            movieDTO.release_date == null ||
            movieDTO.genres?.get(0)?.name == null ||
            movieDTO.backdrop_path == null
        ) {
            AppState.Error(Throwable(CORRUPTED_DATA))
        } else {
            AppState.Success(convertDtoToModel(movieDTO))
        }
    }

    fun saveMovieToDB(movie: Movie) = Thread { localRepository.saveEntity(movie) }.start()

    fun saveNoteToDB(note: String, movieId: Int) = Thread {
        localRepository.saveNote(note, movieId)
    }.start()

    fun getNoteFromDB(movieId: Int) = Thread {
        noteLiveData.postValue(localRepository.getNote(movieId))
    }.start()
}