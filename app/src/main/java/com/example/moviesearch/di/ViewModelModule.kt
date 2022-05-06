package com.example.moviesearch.di

import androidx.lifecycle.ViewModel
import com.example.moviesearch.repository.DetailsRepository
import com.example.moviesearch.repository.LocalRepository
import com.example.moviesearch.repository.MainRepository
import com.example.moviesearch.viewmodel.DetailsViewModel
import com.example.moviesearch.viewmodel.HistoryViewModel
import com.example.moviesearch.viewmodel.ListViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ViewModelModule {

    @IntoMap
    @ViewModelKey(ListViewModel::class)
    @Provides
    fun provideListViewModel(mainRepository: MainRepository): ViewModel {
        return ListViewModel(mainRepository)
    }

    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    @Provides
    fun provideHistoryViewModel(historyRepository: LocalRepository): ViewModel {
        return HistoryViewModel(historyRepository)
    }

    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    @Provides
    fun provideDetailsViewModel(
        detailsRepositoryImpl: DetailsRepository,
        historyRepository: LocalRepository
    ): ViewModel {
        return DetailsViewModel(detailsRepositoryImpl, historyRepository)
    }
}
