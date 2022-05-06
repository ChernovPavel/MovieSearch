package com.example.moviesearch.di

import com.example.moviesearch.view.main.ListFragment
import dagger.Component

@Component(modules = [UiModule::class, DataModule::class])
interface AppComponent {

 fun inject(listFragment: ListFragment)
}