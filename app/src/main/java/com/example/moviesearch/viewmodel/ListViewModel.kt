package com.example.moviesearch.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.di.ListScope
import com.example.moviesearch.model.MoviesResponse
import com.example.moviesearch.repository.ListRepository
import com.example.moviesearch.utils.CORRUPTED_DATA
import com.example.moviesearch.utils.IS_RUSSIAN_LANGUAGE
import com.example.moviesearch.utils.SERVER_ERROR
import com.example.moviesearch.utils.convertMoviesResponseToModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@ListScope
class ListViewModel @Inject constructor(
    private val listRepository: ListRepository,
    context: Context
) : ViewModel() {

    private val _liveListMoviesToObserve: MutableStateFlow<AppState> = MutableStateFlow(AppState.Loading)
    val liveListMoviesToObserve = _liveListMoviesToObserve.asStateFlow()

    private val isRuLanguage =
        context.getSharedPreferences("view.MainActivity", Context.MODE_PRIVATE)
            .getBoolean(IS_RUSSIAN_LANGUAGE, false)

    init {
        viewModelScope.launch {
            _liveListMoviesToObserve.value = AppState.Loading
            try {
                val response = listRepository.getTopMoviesFromServer(isRuLanguage)

                val appState = checkResponse(response)
                _liveListMoviesToObserve.emit(appState)
            } catch (exp: IOException) {
                _liveListMoviesToObserve.value = AppState.Error(Throwable(SERVER_ERROR))
            }
        }
    }

    private fun checkResponse(response: Response<MoviesResponse>): AppState {
        if (response.isSuccessful) {
            val body = response.body()
            body?.let {
                return if (body.results.isEmpty()) {
                    AppState.Error(Throwable(CORRUPTED_DATA))
                } else {
                    AppState.Success(convertMoviesResponseToModel(body))
                }
            }
        }
        return AppState.Error(Throwable(SERVER_ERROR))
    }
}