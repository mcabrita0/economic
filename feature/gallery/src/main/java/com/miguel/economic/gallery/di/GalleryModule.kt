package com.miguel.economic.gallery.di

import com.miguel.economic.gallery.GalleryViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun koinGalleryModule() = module {
    single<CoroutineDispatcher> { Dispatchers.IO }
    viewModelOf(::GalleryViewModel)

}