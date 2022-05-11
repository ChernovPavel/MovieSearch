package com.example.moviesearch.app

import android.app.Application
import com.example.moviesearch.di.AppComponent
import com.example.moviesearch.di.AppModule
import com.example.moviesearch.di.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .build()
}