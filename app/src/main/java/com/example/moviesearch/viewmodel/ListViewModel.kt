package com.example.moviesearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.di.ListScope
import com.example.moviesearch.repository.ListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ListScope
class ListViewModel @Inject constructor(
    private val listRepository: ListRepository,
) : ViewModel() {

    private val _appState: MutableStateFlow<AppState> = MutableStateFlow(AppState.Loading)
    val appState = _appState.asStateFlow()

    fun getListTopMoviesFromAPI(isRuLanguage: Boolean) {
        viewModelScope.launch {
            _appState.value = AppState.Loading

            val state = listRepository.getTopMoviesFromServer(isRuLanguage)
            _appState.emit(state)
        }
    }
}