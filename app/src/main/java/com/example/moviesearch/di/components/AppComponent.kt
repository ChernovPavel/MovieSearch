package com.example.moviesearch.di.components

import com.example.moviesearch.di.modules.AppModule
import com.example.moviesearch.room.HistoryDao
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {

    fun getHistoryDao(): HistoryDao
}