package com.example.moviesearch.di

import com.example.moviesearch.view.history.HistoryFragment
import dagger.Subcomponent

@HistoryScope
@Subcomponent(
    modules = [
        HistoryModule::class
    ]
)
interface HistoryComponent {

    fun inject(historyFragment: HistoryFragment)

}