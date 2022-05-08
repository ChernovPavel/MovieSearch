package com.example.moviesearch.di

import androidx.lifecycle.ViewModel
import com.example.moviesearch.repository.DetailsRepository
import com.example.moviesearch.repository.LocalRepository
import com.example.moviesearch.repository.ListRepository
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
    fun provideListViewModel(listRepository: ListRepository): ViewModel {
        return ListViewModel(listRepository)
    }

    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    @Provides
    fun provideHistoryViewModel(localRepository: LocalRepository): ViewModel {
        return HistoryViewModel(localRepository)
    }

    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    @Provides
    fun provideDetailsViewModel(
        detailsRepositoryImpl: DetailsRepository,
        localRepository: LocalRepository
    ): ViewModel {
        return DetailsViewModel(detailsRepositoryImpl, localRepository)
    }
}
