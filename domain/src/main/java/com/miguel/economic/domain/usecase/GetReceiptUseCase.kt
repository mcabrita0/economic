package com.miguel.economic.domain.usecase

import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.domain.repository.PhotoRepository
import com.miguel.economic.domain.repository.ReceiptRepository

class GetReceiptUseCase(
    private val photoRepository: PhotoRepository,
    private val receiptRepository: ReceiptRepository
) {

    operator fun invoke(): ReceiptModel {
        return TODO()
    }
}