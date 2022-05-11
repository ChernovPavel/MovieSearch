package com.example.moviesearch.di

import android.content.Context
import androidx.room.Room
import com.example.moviesearch.repository.*
import com.example.moviesearch.repository.api.RemoteDataSource
import com.example.moviesearch.room.HistoryDao
import com.example.moviesearch.room.HistoryDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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

    @Singleton
    @Provides
    fun provideLocalRepository(localDataSource: HistoryDao): LocalRepository {
        return LocalRepositoryImpl(localDataSource)
    }

    @Provides
    fun provideHistoryDao(context: Context): HistoryDao {
        val dbName = "History.db"
        val db = context.let {
            Room.databaseBuilder(
                it,
                HistoryDataBase::class.java,
                dbName
            )
                .build()
        }
        return db.historyDao()
    }
}
