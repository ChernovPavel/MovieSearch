package com.example.moviesearch.di

import com.example.moviesearch.app.App
import com.example.moviesearch.repository.*
import com.example.moviesearch.repository.api.RemoteDataSource
import com.example.moviesearch.room.HistoryDao
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideMainRepository(remoteDataSource: RemoteDataSource): ListRepository {
        return ListRepositoryImpl(remoteDataSource)
    }

    @Provides
    fun provideDetailsRepository(remoteDataSource: RemoteDataSource): DetailsRepository {
        return DetailsRepositoryImpl(remoteDataSource)
    }

    @AppScope
    @Provides
    fun provideLocalRepository(localDataSource: HistoryDao): LocalRepository {
        return LocalRepositoryImpl(localDataSource)
    }

    @Provides
    fun provideHistoryDao(): HistoryDao {
        return App.getHistoryDao()
    }
}
