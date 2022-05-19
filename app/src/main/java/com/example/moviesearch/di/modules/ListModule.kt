package com.example.moviesearch.di.modules

import androidx.lifecycle.ViewModel
import com.example.moviesearch.di.ListScope
import com.example.moviesearch.di.ViewModelKey
import com.example.moviesearch.repository.ListRepository
import com.example.moviesearch.repository.ListRepositoryImpl
import com.example.moviesearch.viewmodel.ListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ListModule {

    @ListScope
    @Binds
    fun provideListRepository(listRepository: ListRepositoryImpl): ListRepository

    @ListScope
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    @Binds
    fun provideListViewModel(listViewModel: ListViewModel): ViewModel
}