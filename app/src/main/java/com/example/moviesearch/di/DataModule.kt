package com.example.moviesearch.di

import com.example.moviesearch.repository.DetailsRepository
import com.example.moviesearch.repository.DetailsRepositoryImpl
import com.example.moviesearch.repository.api.RemoteDataSource
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideDetailsRepository(remoteDataSource: RemoteDataSource): DetailsRepository {
        return DetailsRepositoryImpl(remoteDataSource)
    }
}
