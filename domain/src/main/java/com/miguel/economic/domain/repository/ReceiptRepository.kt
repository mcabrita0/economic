package com.miguel.economic.domain.repository

import com.miguel.economic.domain.model.ReceiptModel

interface ReceiptRepository {

    fun getReceipts(): List<ReceiptModel>

    fun getReceipt(id: Int): ReceiptModel
}
