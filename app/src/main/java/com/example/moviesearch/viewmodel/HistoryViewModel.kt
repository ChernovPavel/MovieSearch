package com.example.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesearch.app.App
import com.example.moviesearch.repository.LocalRepository
import com.example.moviesearch.repository.LocalRepositoryImpl

class HistoryViewModel(private val historyRepository: LocalRepository) : ViewModel() {

    val historyLiveData: MutableLiveData<AppState> = MutableLiveData()

    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        Thread {
            historyLiveData.postValue(AppState.Success(historyRepository.getAllHistory()))
        }.start()
    }
}

class HistoryViewModelFactory : ViewModelProvider.Factory {

    private val localRepo = LocalRepositoryImpl(App.getHistoryDao())

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = HistoryViewModel(localRepo)
        return viewModel as T
    }
}