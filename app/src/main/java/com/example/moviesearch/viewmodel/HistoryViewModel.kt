package com.example.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.repository.LocalRepository

class HistoryViewModel(private val localRepository: LocalRepository) : ViewModel() {

    val historyLiveData: MutableLiveData<AppState> = MutableLiveData()

    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        Thread {
            historyLiveData.postValue(AppState.Success(localRepository.getAllHistory()))
        }.start()
    }
}