package com.example.moviesearch.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.di.ListScope
import com.example.moviesearch.repository.ListRepository
import com.example.moviesearch.utils.IS_RUSSIAN_LANGUAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
            listRepository.getTopMoviesFromServer(isRuLanguage).collect {
                _liveListMoviesToObserve.emit(it)
            }
        }
    }
}