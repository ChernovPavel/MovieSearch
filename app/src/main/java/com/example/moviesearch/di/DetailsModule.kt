package com.example.moviesearch.di

import androidx.lifecycle.ViewModel
import com.example.moviesearch.repository.DetailsRepository
import com.example.moviesearch.repository.DetailsRepositoryImpl
import com.example.moviesearch.repository.LocalRepository
import com.example.moviesearch.repository.api.RemoteDataSource
import com.example.moviesearch.viewmodel.DetailsViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class DetailsModule {

    @DetailsScope
    @Provides
    fun provideDetailsRepository(remoteDataSource: RemoteDataSource): DetailsRepository {
        return DetailsRepositoryImpl(remoteDataSource)
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