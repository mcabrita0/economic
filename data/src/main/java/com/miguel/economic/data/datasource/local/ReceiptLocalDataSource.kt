package com.miguel.economic.data.datasource.local

import com.miguel.economic.data.model.ReceiptDbModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class ReceiptLocalDataSource(
    private val receiptDatabase: ReceiptDatabase,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun insertOrUpdate(receipt: ReceiptDbModel) = withContext(dispatcher) {
        receiptDatabase.receiptDao().insertOrUpdate(receipt)
    }

    suspend fun getAll(): List<ReceiptDbModel> = withContext(dispatcher) {
        receiptDatabase.receiptDao().getAll()
    }

    suspend fun get(id: Int): ReceiptDbModel = withContext(dispatcher) {
        receiptDatabase.receiptDao().get(id)
    }
}