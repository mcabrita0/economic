package com.miguel.economic.domain.repository

import com.miguel.economic.domain.model.ReceiptModel

interface ReceiptRepository {

    suspend fun getReceipts(): List<ReceiptModel>

    suspend fun getReceipt(id: Int): ReceiptModel

    suspend fun createOrUpdateReceipt(receipt: ReceiptModel)
}
