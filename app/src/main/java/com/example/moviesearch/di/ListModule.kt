package com.example.moviesearch.di

import androidx.lifecycle.ViewModel
import com.example.moviesearch.repository.ListRepository
import com.example.moviesearch.repository.ListRepositoryImpl
import com.example.moviesearch.repository.api.RemoteDataSource
import com.example.moviesearch.viewmodel.ListViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ListModule {

    @Provides
    fun provideListRepository(remoteDataSource: RemoteDataSource): ListRepository {
        return ListRepositoryImpl(remoteDataSource)
    }

    @IntoMap
    @ViewModelKey(ListViewModel::class)
    @Provides
    fun provideListViewModel(listRepository: ListRepository): ViewModel {
        return ListViewModel(listRepository)
    }
}