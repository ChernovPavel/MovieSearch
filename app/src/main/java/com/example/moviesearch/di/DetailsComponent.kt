package com.example.moviesearch.di

import com.example.moviesearch.view.details.DetailsFragment
import dagger.Subcomponent

@DetailsScope
@Subcomponent(
    modules = [
        DetailsModule::class
    ]
)
interface DetailsComponent {

    fun inject(detailsFragment: DetailsFragment)

}