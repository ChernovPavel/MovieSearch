package com.example.moviesearch.di.components

import com.example.moviesearch.di.HistoryScope
import com.example.moviesearch.di.modules.HistoryModule
import com.example.moviesearch.view.history.HistoryFragment
import dagger.Component

@HistoryScope
@Component(
    dependencies = [AppComponent::class],
    modules = [HistoryModule::class]
)
interface HistoryComponent {

    fun inject(historyFragment: HistoryFragment)
}