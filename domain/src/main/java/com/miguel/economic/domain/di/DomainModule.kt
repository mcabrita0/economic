package com.miguel.economic.domain.di

import com.miguel.economic.domain.usecase.CreateOrUpdateReceiptUseCase
import com.miguel.economic.domain.usecase.CreatePhotoFileUseCase
import com.miguel.economic.domain.usecase.GetReceiptUseCase
import com.miguel.economic.domain.usecase.GetReceiptsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun koinDomainModule() = module {
    single<CoroutineDispatcher> { Dispatchers.IO }
    factoryOf(::GetReceiptUseCase)
    factoryOf(::GetReceiptsUseCase)
    factoryOf(::CreatePhotoFileUseCase)
    factoryOf(::CreateOrUpdateReceiptUseCase)
}