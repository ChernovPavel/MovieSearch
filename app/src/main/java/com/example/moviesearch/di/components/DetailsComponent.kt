package com.example.moviesearch.di.components

import com.example.moviesearch.di.DetailsScope
import com.example.moviesearch.di.modules.DetailsModule
import com.example.moviesearch.view.details.DetailsFragment
import dagger.Component

@DetailsScope
@Component(
    dependencies = [LocalRepositoryComponent::class, RemoteDataComponent::class],
    modules = [DetailsModule::class]
)
interface DetailsComponent {

    fun inject(detailsFragment: DetailsFragment)
}