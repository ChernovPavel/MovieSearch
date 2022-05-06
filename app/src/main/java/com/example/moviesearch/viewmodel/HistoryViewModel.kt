package com.example.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.app.App.Companion.getHistoryDao
import com.example.moviesearch.repository.LocalRepository
import com.example.moviesearch.repository.LocalRepositoryImpl

class HistoryViewModel(
    private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())
) : ViewModel() {

    val historyLiveData: MutableLiveData<AppState> = MutableLiveData()

    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        Thread {
            historyLiveData.postValue(AppState.Success(historyRepository.getAllHistory()))
        }.start()
    }
}