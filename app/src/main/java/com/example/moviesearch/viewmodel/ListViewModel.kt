package com.example.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.model.MoviesResponse
import com.example.moviesearch.repository.ListRepository
import com.example.moviesearch.utils.CORRUPTED_DATA
import com.example.moviesearch.utils.SERVER_ERROR
import com.example.moviesearch.utils.convertMoviesResponseToModel
import kotlinx.coroutines.launch
import java.io.IOException

class ListViewModel(private val listRepository: ListRepository) : ViewModel() {

    val liveListMoviesToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun getListTopMoviesFromAPI(isRuLanguage: Boolean) {
        liveListMoviesToObserve.value = AppState.Loading

        viewModelScope.launch {
            try {
                val responseBody = listRepository.getTopMoviesFromServer(isRuLanguage).body()

                responseBody?.let {
                    liveListMoviesToObserve.postValue(checkResponse(it))
                }
            } catch (exp: IOException) {
                liveListMoviesToObserve.postValue(AppState.Error(Throwable(SERVER_ERROR)))
            }
        }
    }

    private fun checkResponse(listMovies: MoviesResponse): AppState {
        return if (listMovies.results.isEmpty()) {
            AppState.Error(Throwable(CORRUPTED_DATA))
        } else {
            AppState.Success(convertMoviesResponseToModel(listMovies))
        }
    }
}