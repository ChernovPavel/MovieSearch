package com.example.moviesearch.di.components

import com.example.moviesearch.di.modules.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {

    fun getListComponent(): ListSubcomponent
    fun getDetailsComponent(): DetailsSubcomponent
    fun getHistoryComponent(): HistorySubcomponent

}