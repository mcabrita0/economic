package com.miguel.economic.domain.usecase

import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.domain.repository.ReceiptRepository

class GetReceiptUseCase(
    private val receiptRepository: ReceiptRepository
) {

    suspend operator fun invoke(): ReceiptModel {
        return TODO()
    }
}