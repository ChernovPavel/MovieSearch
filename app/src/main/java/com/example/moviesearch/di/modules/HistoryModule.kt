package com.example.moviesearch.di.modules

import androidx.lifecycle.ViewModel
import com.example.moviesearch.di.HistoryScope
import com.example.moviesearch.di.ViewModelKey
import com.example.moviesearch.repository.LocalRepository
import com.example.moviesearch.viewmodel.HistoryViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class HistoryModule {

    @HistoryScope
    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    @Provides
    fun provideHistoryViewModel(localRepository: LocalRepository): ViewModel {
        return HistoryViewModel(localRepository)
    }
}