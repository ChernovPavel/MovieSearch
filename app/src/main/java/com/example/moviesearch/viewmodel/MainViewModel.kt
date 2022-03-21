package com.example.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.model.Movie
import com.example.moviesearch.model.Repo
import com.example.moviesearch.model.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel : ViewModel() {

    private val liveListMoviesToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repositoryImpl: Repo = RepositoryImpl()

    fun getLiveData() = liveListMoviesToObserve

    fun getMovieFromLocalSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveListMoviesToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveListMoviesToObserve.postValue(AppState.Success(repositoryImpl.getMovieFromLocalStorage()))
        }.start()
    }

    val selectedItem: MutableLiveData<Movie> = MutableLiveData()

    fun select(movie: Movie) {
        selectedItem.value = movie
    }
}