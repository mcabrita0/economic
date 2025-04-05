package com.miguel.economic.data.repository

import com.miguel.economic.data.datasource.local.ReceiptLocalDataSource
import com.miguel.economic.data.mapper.toDbModel
import com.miguel.economic.data.mapper.toModel
import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.domain.repository.ReceiptRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class ReceiptRepositoryImpl(
    private val receiptLocalDataSource: ReceiptLocalDataSource,
    private val dispatcher: CoroutineDispatcher
) : ReceiptRepository {

    override suspend fun getReceipts(): List<ReceiptModel> = withContext(dispatcher) {
        receiptLocalDataSource.getAll().map {
            it.toModel()
        }
    }

    override suspend fun getReceipt(id: Int): ReceiptModel = withContext(dispatcher) {
        receiptLocalDataSource.get(id).toModel()
    }

    override suspend fun createOrUpdateReceipt(receipt: ReceiptModel) = withContext(dispatcher) {
        receiptLocalDataSource.insertOrUpdate(receipt.toDbModel())
    }
}