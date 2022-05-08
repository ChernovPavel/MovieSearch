package com.example.moviesearch.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import javax.inject.Scope
import kotlin.reflect.KClass

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope
