package com.example.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.di.HistoryScope
import com.example.moviesearch.repository.LocalRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HistoryScope
class HistoryViewModel @Inject constructor(private val localRepository: LocalRepository) :
    ViewModel() {

    val historyLiveData: MutableLiveData<AppState> = MutableLiveData()

    fun getAllHistory() {
        historyLiveData.value = AppState.Loading

        viewModelScope.launch {
            val data = localRepository.getAllHistory()
            historyLiveData.postValue(AppState.Success(data))
        }
    }
}