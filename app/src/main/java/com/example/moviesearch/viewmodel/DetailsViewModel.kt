package com.example.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.di.DetailsScope
import com.example.moviesearch.model.Movie
import com.example.moviesearch.model.MovieDTO
import com.example.moviesearch.repository.DetailsRepository
import com.example.moviesearch.repository.LocalRepository
import com.example.moviesearch.utils.CORRUPTED_DATA
import com.example.moviesearch.utils.SERVER_ERROR
import com.example.moviesearch.utils.convertDtoToModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@DetailsScope
class DetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    val detailsLiveData: MutableLiveData<AppState> = MutableLiveData()
    val noteLiveData: MutableLiveData<String> = MutableLiveData()

    fun getMovieFromAPI(movieId: Int) {
        detailsLiveData.value = AppState.Loading

        viewModelScope.launch {
            try {
                val response = detailsRepository.getMovieDetailsFromServer(movieId)
                if (response.isSuccessful) {
                    val data = response.body()
                    data?.let {
                        detailsLiveData.value = checkResponse(it)
                    }
                }
            } catch (exp: IOException) {
                detailsLiveData.value = AppState.Error(Throwable(SERVER_ERROR))
            }
        }
    }

    private fun checkResponse(movieDTO: MovieDTO): AppState {
        return if (movieDTO.title == null ||
            movieDTO.overview == null ||
            movieDTO.releaseDate == null ||
            movieDTO.genres?.get(0)?.name == null ||
            movieDTO.backdropPath == null
        ) {
            AppState.Error(Throwable(CORRUPTED_DATA))
        } else {
            AppState.Success(convertDtoToModel(movieDTO))
        }
    }

    fun saveMovieToDB(movie: Movie) {
        viewModelScope.launch {
            localRepository.saveEntity(movie)
        }
    }

    fun saveNoteToDB(note: String, movieId: Int) {
        viewModelScope.launch {
            localRepository.saveNote(note, movieId)
        }
    }

    fun getNoteFromDB(movieId: Int) {
        viewModelScope.launch {
            noteLiveData.postValue(localRepository.getNote(movieId))
        }
    }
}