package com.example.moviesearch.di.components

import com.example.moviesearch.di.ListScope
import com.example.moviesearch.di.modules.ListModule
import com.example.moviesearch.view.main.ListFragment
import dagger.Component

@ListScope
@Component(
    dependencies = [RemoteDataComponent::class],
    modules = [ListModule::class]
)
interface ListComponent {

    fun inject(listFragment: ListFragment)
}