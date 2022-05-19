package com.example.moviesearch.di.components

import com.example.moviesearch.di.DetailsScope
import com.example.moviesearch.di.modules.DetailsModule
import com.example.moviesearch.view.details.DetailsFragment
import dagger.Subcomponent

@DetailsScope
@Subcomponent(
    modules = [
        DetailsModule::class
    ]
)
interface DetailsSubcomponent {

    fun inject(detailsFragment: DetailsFragment)

}