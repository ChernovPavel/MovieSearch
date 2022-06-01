package com.example.moviesearch.di.components

import com.example.moviesearch.di.LocalRepositoryScope
import com.example.moviesearch.di.modules.LocalRepositoryModule
import com.example.moviesearch.repository.LocalRepository
import dagger.Component

@LocalRepositoryScope
@Component(
    dependencies = [AppComponent::class],
    modules = [LocalRepositoryModule::class]
)
interface LocalRepositoryComponent {


    fun getLocalRepository(): LocalRepository
}