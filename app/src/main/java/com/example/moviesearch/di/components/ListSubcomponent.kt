package com.example.moviesearch.di.components

import com.example.moviesearch.di.ListScope
import com.example.moviesearch.di.modules.ListModule
import com.example.moviesearch.view.main.ListFragment
import dagger.Subcomponent

@ListScope
@Subcomponent(
    modules = [
        ListModule::class
    ]
)
interface ListSubcomponent {

    fun inject(listFragment: ListFragment)

}