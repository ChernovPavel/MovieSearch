package com.example.moviesearch.di.components

import com.example.moviesearch.di.HistoryScope
import com.example.moviesearch.di.modules.HistoryModule
import com.example.moviesearch.view.history.HistoryFragment
import dagger.Subcomponent

@HistoryScope
@Subcomponent(
    modules = [
        HistoryModule::class
    ]
)
interface HistorySubcomponent {

    fun inject(historyFragment: HistoryFragment)

}