package com.miguel.economic.domain.usecase

import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.domain.repository.ReceiptRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CreateOrUpdateReceiptUseCase(
    private val receiptRepository: ReceiptRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(receipt: ReceiptModel) = withContext(dispatcher) {
        receiptRepository.createOrUpdateReceipt(receipt)
    }
}