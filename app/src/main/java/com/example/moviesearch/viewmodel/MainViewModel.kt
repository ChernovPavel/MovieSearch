package com.example.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.model.Repo
import com.example.moviesearch.model.RepositoryImpl

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData(),
    private val repositoryImpl: Repo = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve
}