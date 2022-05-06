package com.example.moviesearch.di

import com.example.moviesearch.repository.*
import com.example.moviesearch.repository.api.RemoteDataSource
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideMainRepository(remoteDataSource: RemoteDataSource): MainRepository {
        return MainRepositoryImpl(remoteDataSource)
    }
}
