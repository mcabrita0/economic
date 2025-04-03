package com.miguel.economic.domain.usecase

import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.domain.repository.PhotoRepository
import com.miguel.economic.domain.repository.ReceiptRepository

class GetReceiptsUseCase(
    private val photoRepository: PhotoRepository,
    private val receiptRepository: ReceiptRepository
) {

    operator fun invoke(): List<ReceiptModel> {
        return emptyList() //TODO: implement
    }
}
