package com.example.moviesearch.di.modules

import androidx.lifecycle.ViewModel
import com.example.moviesearch.di.DetailsScope
import com.example.moviesearch.di.ViewModelKey
import com.example.moviesearch.repository.DetailsRepository
import com.example.moviesearch.repository.DetailsRepositoryImpl
import com.example.moviesearch.viewmodel.DetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface DetailsModule {

    @DetailsScope
    @Binds
    fun provideDetailsRepository(detailsRepository: DetailsRepositoryImpl): DetailsRepository

    @DetailsScope
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    @Binds
    fun provideDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModel
}