package com.miguel.economic.domain.usecase

import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.domain.repository.ReceiptRepository

class CreateReceiptUseCase(
    private val receiptRepository: ReceiptRepository
) {

    suspend operator fun invoke(receipt: ReceiptModel) {

    }
}