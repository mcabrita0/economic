package com.miguel.economic.data.di

import com.miguel.economic.data.repository.PhotoRepositoryImpl
import com.miguel.economic.data.repository.ReceiptRepositoryImpl
import com.miguel.economic.domain.repository.PhotoRepository
import com.miguel.economic.domain.repository.ReceiptRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun koinModuleData() = module {
    singleOf(::PhotoRepositoryImpl) { bind<PhotoRepository>() }
    singleOf(::ReceiptRepositoryImpl) { bind<ReceiptRepository>() }
}