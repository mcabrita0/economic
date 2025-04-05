package com.miguel.economic.data.di

import com.miguel.economic.data.repository.CameraRepositoryImpl
import com.miguel.economic.data.repository.ReceiptRepositoryImpl
import com.miguel.economic.domain.repository.CameraRepository
import com.miguel.economic.domain.repository.ReceiptRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun koinDataModule() = module {
    singleOf(::ReceiptRepositoryImpl) { bind<ReceiptRepository>() }
    singleOf(::CameraRepositoryImpl) { bind<CameraRepository>() }
}