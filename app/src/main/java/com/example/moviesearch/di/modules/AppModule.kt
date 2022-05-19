package com.example.moviesearch.di.modules

import android.content.Context
import androidx.room.Room
import com.example.moviesearch.repository.LocalRepository
import com.example.moviesearch.repository.LocalRepositoryImpl
import com.example.moviesearch.repository.api.MovieAPI
import com.example.moviesearch.room.HistoryDao
import com.example.moviesearch.room.HistoryDataBase
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    private val baseUrl = "https://api.themoviedb.org/"

    @Provides
    fun getAppContext(): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideLocalRepository(localDataSource: HistoryDao): LocalRepository {
        return LocalRepositoryImpl(localDataSource)
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

    @Singleton
    @Provides
    fun provideRetrofitInterface(retrofit: Retrofit): MovieAPI {
        return retrofit.create(MovieAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
    }
}