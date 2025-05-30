package com.miguel.economic.domain.usecase

import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.domain.repository.ReceiptRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetReceiptUseCase(
    private val receiptRepository: ReceiptRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(id: Int): ReceiptModel = withContext(dispatcher) {
        receiptRepository.getReceipt(id)
    }
}