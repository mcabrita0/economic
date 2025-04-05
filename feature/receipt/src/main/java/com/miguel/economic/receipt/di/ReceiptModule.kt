package com.miguel.economic.receipt.di

import com.miguel.economic.receipt.ReceiptViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun koinReceiptModule() = module {
    single<CoroutineDispatcher> { Dispatchers.IO }
    viewModelOf(::ReceiptViewModel)
}