package com.example.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.model.ListMoviesDTO
import com.example.moviesearch.model.MovieItem
import com.example.moviesearch.model.Repo
import com.example.moviesearch.model.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel : ViewModel() {

    private val liveListMoviesToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val internetLiveListMoviesToObserve: MutableLiveData<ListMoviesDTO> = MutableLiveData(),
    private val repositoryImpl: Repo = RepositoryImpl()

    fun getLiveData() = liveListMoviesToObserve
    fun getInternetLiveData() = internetLiveListMoviesToObserve

    fun getMovieFromLocalSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveListMoviesToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveListMoviesToObserve.postValue(AppState.Success(repositoryImpl.getMovieFromLocalStorage()))
        }.start()
    }

    val selectedItem: MutableLiveData<MovieItem> = MutableLiveData()

    fun select(movie: MovieItem) {
        selectedItem.value = movie
    }

    fun setDataFromInternet(listMoviesDTO: ListMoviesDTO) {
        Thread {
            internetLiveListMoviesToObserve.postValue(listMoviesDTO)
        }.start()
    }

}