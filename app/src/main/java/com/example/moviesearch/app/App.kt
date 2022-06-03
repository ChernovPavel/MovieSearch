package com.example.moviesearch.app

import android.app.Application
import com.example.moviesearch.di.components.*
import com.example.moviesearch.di.modules.AppModule

class App : Application() {

    val appComponent: AppComponent = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .build()

    val localRepositoryComponent: LocalRepositoryComponent = DaggerLocalRepositoryComponent.builder()
        .appComponent(appComponent)
        .build()

    val remoteDataComponent: RemoteDataComponent = DaggerRemoteDataComponent.builder()
        .build()
}