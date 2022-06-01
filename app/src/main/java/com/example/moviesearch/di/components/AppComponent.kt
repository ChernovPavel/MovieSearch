package com.example.moviesearch.di.components

import com.example.moviesearch.di.modules.AppModule
import com.example.moviesearch.repository.LocalRepository
import com.example.moviesearch.repository.api.RemoteDataSource
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {

    fun getLocalRepository(): LocalRepository
    fun getRemoteDataSource(): RemoteDataSource
}