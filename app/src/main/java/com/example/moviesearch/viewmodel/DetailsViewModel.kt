package com.example.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesearch.model.Movie
import com.example.moviesearch.model.MovieDTO
import com.example.moviesearch.repository.DetailsRepository
import com.example.moviesearch.repository.LocalRepository
import com.example.moviesearch.utils.CORRUPTED_DATA
import com.example.moviesearch.utils.REQUEST_ERROR
import com.example.moviesearch.utils.SERVER_ERROR
import com.example.moviesearch.utils.convertDtoToModel
import javax.inject.Inject

class DetailsViewModel(
    private val detailsRepositoryImpl: DetailsRepository,
    private val historyRepository: LocalRepository
) : ViewModel() {

    val detailsLiveData: MutableLiveData<AppState> = MutableLiveData()
    val noteLiveData: MutableLiveData<String> = MutableLiveData()

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
            movieDTO.backdrop_path == null
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

class DetailsViewModelFactory @Inject constructor(
    private val detailsRepo: DetailsRepository,
    private val historyRepo: LocalRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = DetailsViewModel(detailsRepo, historyRepo)
        return viewModel as T
    }
}