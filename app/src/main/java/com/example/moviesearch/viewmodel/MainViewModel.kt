package com.example.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.model.MoviesResponse
import com.example.moviesearch.repository.MainRepo
import com.example.moviesearch.repository.MainRepositoryImpl
import com.example.moviesearch.repository.RemoteDataSource
import com.example.moviesearch.utils.convertMoviesResponseToModel
import retrofit2.Call
import retrofit2.Response
import java.lang.Thread.sleep

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class MainViewModel : ViewModel() {

    val liveListMoviesToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repositoryImpl: MainRepo = MainRepositoryImpl(RemoteDataSource())

    fun getMovieFromLocalSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveListMoviesToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveListMoviesToObserve.postValue(AppState.Success(repositoryImpl.getMovieFromLocalStorage()))
        }.start()
    }

    fun getListTopMoviesFromAPI(){
        liveListMoviesToObserve.value = AppState.Loading
        repositoryImpl.getTopMoviesFromServer(callback)
    }

    private val callback = object : retrofit2.Callback<MoviesResponse> {
        override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
            val serverResponse: MoviesResponse? = response.body()
            liveListMoviesToObserve.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
            liveListMoviesToObserve.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
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