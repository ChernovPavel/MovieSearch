package com.example.moviesearch.di.modules

import android.content.Context
import androidx.room.Room
import com.example.moviesearch.room.HistoryDao
import com.example.moviesearch.room.HistoryDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    fun getAppContext(): Context {
        return context
    }

    @Singleton
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


