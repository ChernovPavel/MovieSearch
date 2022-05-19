package com.example.moviesearch.app

import android.app.Application
import com.example.moviesearch.di.components.AppComponent
import com.example.moviesearch.di.components.DaggerAppComponent
import com.example.moviesearch.di.modules.AppModule

class App : Application() {

    val appComponent: AppComponent = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .build()
}