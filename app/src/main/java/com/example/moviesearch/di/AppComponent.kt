package com.example.moviesearch.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {

    fun getListComponent(): ListComponent
    fun getDetailsComponent(): DetailsComponent
    fun getHistoryComponent(): HistoryComponent

}