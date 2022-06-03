package com.example.moviesearch.di.components

import com.example.moviesearch.di.RemoteDataScope
import com.example.moviesearch.di.modules.RemoteDataModule
import com.example.moviesearch.repository.api.RemoteDataSource
import dagger.Component

@RemoteDataScope
@Component(
    modules = [RemoteDataModule::class]
)
interface RemoteDataComponent {

    fun getRemoteDataSource(): RemoteDataSource
}