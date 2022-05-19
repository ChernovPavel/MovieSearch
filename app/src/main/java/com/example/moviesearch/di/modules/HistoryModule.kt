package com.example.moviesearch.di.modules

import androidx.lifecycle.ViewModel
import com.example.moviesearch.di.HistoryScope
import com.example.moviesearch.di.ViewModelKey
import com.example.moviesearch.viewmodel.HistoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface HistoryModule {

    @HistoryScope
    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    @Binds
    fun provideHistoryViewModel(historyViewModel: HistoryViewModel): ViewModel
}