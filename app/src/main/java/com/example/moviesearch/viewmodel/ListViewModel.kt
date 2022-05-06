package com.example.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesearch.model.MoviesResponse
import com.example.moviesearch.repository.MainRepo
import com.example.moviesearch.utils.CORRUPTED_DATA
import com.example.moviesearch.utils.REQUEST_ERROR
import com.example.moviesearch.utils.SERVER_ERROR
import com.example.moviesearch.utils.convertMoviesResponseToModel
import retrofit2.Call
import retrofit2.Response

class ListViewModel(private val repositoryImpl: MainRepo) : ViewModel() {

    val liveListMoviesToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun getListTopMoviesFromAPI(isRuLanguage: Boolean) {
        liveListMoviesToObserve.value = AppState.Loading
        repositoryImpl.getTopMoviesFromServer(isRuLanguage, callback)
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

class ListViewModelFactory(private val repo: MainRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = ListViewModel(repo)
        return viewModel as T
    }
}