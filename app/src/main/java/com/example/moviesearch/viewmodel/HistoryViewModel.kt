package com.example.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesearch.repository.LocalRepository
import javax.inject.Inject

class HistoryViewModel(private val historyRepository: LocalRepository) : ViewModel() {

    val historyLiveData: MutableLiveData<AppState> = MutableLiveData()

    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        Thread {
            historyLiveData.postValue(AppState.Success(historyRepository.getAllHistory()))
        }.start()
    }
}

class HistoryViewModelFactory @Inject constructor(private val localRepo: LocalRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = HistoryViewModel(localRepo)
        return viewModel as T
    }
}