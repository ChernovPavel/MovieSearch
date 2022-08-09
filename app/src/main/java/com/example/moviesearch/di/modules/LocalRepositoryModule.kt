package com.example.moviesearch.di.modules

import com.example.moviesearch.di.LocalRepositoryScope
import com.example.moviesearch.repository.LocalRepository
import com.example.moviesearch.repository.LocalRepositoryImpl
import com.example.moviesearch.room.HistoryDao
import dagger.Module
import dagger.Provides

@Module
class LocalRepositoryModule {

    @LocalRepositoryScope
    @Provides
    fun provideLocalRepository(localDataSource: HistoryDao): LocalRepository {
        return LocalRepositoryImpl(localDataSource)
    }
}
