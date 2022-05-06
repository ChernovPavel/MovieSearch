package com.example.moviesearch.di

import com.example.moviesearch.view.details.DetailsFragment
import com.example.moviesearch.view.history.HistoryFragment
import com.example.moviesearch.view.main.ListFragment
import dagger.Component

@Component(modules = [UiModule::class, DataModule::class])
interface AppComponent {

 fun inject(listFragment: ListFragment)
 fun inject(listFragment: HistoryFragment)
 fun inject(listFragment: DetailsFragment)
}