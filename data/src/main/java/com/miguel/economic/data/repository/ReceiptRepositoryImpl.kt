package com.miguel.economic.data.repository

import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.domain.repository.ReceiptRepository

internal class ReceiptRepositoryImpl : ReceiptRepository {

    override fun getReceipts(): List<ReceiptModel> {
        return ReceiptModel(
            photoFilename = "",
            amount = 100,
            currencyCode = "EUR",
            createdDate = "2025-04-04 10:10"
        ).let { item ->
            (0 until 100).map { item }
        }
    }

    override fun getReceipt(id: Int): ReceiptModel {
        TODO("Not yet implemented")
    }
}