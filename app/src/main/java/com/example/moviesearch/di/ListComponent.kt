package com.example.moviesearch.di

import com.example.moviesearch.view.main.ListFragment
import dagger.Subcomponent

@ListScope
@Subcomponent(
    modules = [
        ListModule::class
    ]
)
interface ListComponent {

    fun inject(listFragment: ListFragment)

}