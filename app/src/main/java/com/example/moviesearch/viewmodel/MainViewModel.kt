package com.example.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.model.Movie
import com.example.moviesearch.repository.MainRepo
import com.example.moviesearch.repository.MainRepositoryImpl
import java.lang.Thread.sleep

class MainViewModel : ViewModel() {

    private val liveListMoviesToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repositoryImpl: MainRepo = MainRepositoryImpl()

    fun getLiveData() = liveListMoviesToObserve

    fun getMovieFromLocalSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveListMoviesToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveListMoviesToObserve.postValue(AppState.Success(repositoryImpl.getMovieFromLocalStorage()))
        }.start()
    }
}