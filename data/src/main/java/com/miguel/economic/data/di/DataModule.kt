package com.miguel.economic.data.di

import androidx.room.Room
import com.miguel.economic.data.datasource.local.ReceiptDatabase
import com.miguel.economic.data.datasource.local.ReceiptLocalDataSource
import com.miguel.economic.data.repository.CameraRepositoryImpl
import com.miguel.economic.data.repository.ReceiptRepositoryImpl
import com.miguel.economic.domain.repository.CameraRepository
import com.miguel.economic.domain.repository.ReceiptRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun koinDataModule() = module {
    single<ReceiptDatabase> {
        Room.databaseBuilder(
            get(),
            ReceiptDatabase::class.java,
            "receipt"
        ).build()
    }

    singleOf(::ReceiptRepositoryImpl) { bind<ReceiptRepository>() }
    singleOf(::CameraRepositoryImpl) { bind<CameraRepository>() }
    singleOf(::ReceiptLocalDataSource)
}