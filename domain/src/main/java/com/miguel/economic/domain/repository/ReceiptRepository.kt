package com.miguel.economic.domain.repository

import com.miguel.economic.domain.model.ReceiptModel

interface ReceiptRepository {

    fun getReceipts(): List<ReceiptModel>

    fun getReceipt(id: Int): ReceiptModel

    fun createReceipt(receipt: ReceiptModel)

    fun updateReceipt(receipt: ReceiptModel)
}
