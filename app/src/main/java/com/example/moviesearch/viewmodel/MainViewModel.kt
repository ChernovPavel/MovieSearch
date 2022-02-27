package com.example.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.model.*
import java.lang.Thread.sleep

class MainViewModel(
    private val liveListMoviesToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val liveInternetListMoviesToObserve: MutableLiveData<ListMoviesDTO> = MutableLiveData(),
    private val repositoryImpl: Repo = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveListMoviesToObserve

    fun getMovieFromLocalSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveListMoviesToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveListMoviesToObserve.postValue(AppState.Success(repositoryImpl.getMovieFromLocalStorage()))
        }.start()
    }

    val selectedItem: MutableLiveData<MovieItem> = MutableLiveData()

    fun select(movie: Movie) {
        selectedItem.value = movie
    }

     val onLoadListListener: ListMoviesLoader.ListMoviesLoaderListener =
        object : ListMoviesLoader.ListMoviesLoaderListener {
            override fun onLoadedList(listMoviesDTO: ListMoviesDTO) {

                internetLiveListMoviesToObserve.postValue(listMoviesDTO)
            }

            override fun onFailedList(throwable: Throwable) {
                //todo
            }
        }
}