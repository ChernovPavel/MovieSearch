package com.example.moviesearch.di

import com.example.moviesearch.view.details.DetailsFragment
import com.example.moviesearch.view.history.HistoryFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun getListComponent(): ListComponent
//    fun getDetailsComponent(): DetailsComponent
//    fun getHistoryComponent(): HistoryComponent

    fun inject(listFragment: HistoryFragment)
    fun inject(listFragment: DetailsFragment)
}