package com.miguel.economic.domain.di

import com.miguel.economic.domain.usecase.GetReceiptUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun koinDomainModule() = module {
    factoryOf(::GetReceiptUseCase)
    factoryOf(::GetReceiptsUseCase)
}