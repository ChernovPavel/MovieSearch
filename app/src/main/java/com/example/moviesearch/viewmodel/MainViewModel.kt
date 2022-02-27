package com.example.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.R
import com.example.moviesearch.model.ListMoviesDTO
import com.example.moviesearch.model.Movie
import com.example.moviesearch.model.Repo
import com.example.moviesearch.model.RepositoryImpl
import com.example.moviesearch.view.details.ListMoviesLoader
import com.example.moviesearch.view.details.MovieLoader
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_list.*
import java.lang.Thread.sleep

class MainViewModel(
    private val liveListMoviesToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val internetLiveListMoviesToObserve: MutableLiveData<ListMoviesDTO> = MutableLiveData(),
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

    val selectedItem: MutableLiveData<Movie> = MutableLiveData()

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