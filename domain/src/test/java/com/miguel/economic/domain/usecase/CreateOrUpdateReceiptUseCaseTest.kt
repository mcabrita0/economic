package com.miguel.economic.domain.usecase

import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.domain.repository.ReceiptRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDateTime

class CreateOrUpdateReceiptUseCaseTest {

    private val receiptRepository: ReceiptRepository = mockk(relaxed = true)
    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    private val createOrUpdateReceiptUseCase = CreateOrUpdateReceiptUseCase(
        receiptRepository = receiptRepository,
        dispatcher = dispatcher
    )

    @Test
    fun `When invoked then call repository`() = runTest(dispatcher) {
        // Given
        val receipt = ReceiptModel(
            photoFilename = "file",
            amount = 123.5f,
            currencyCode = "EUR",
            createdDate = LocalDateTime.now()
        )

        // When
        createOrUpdateReceiptUseCase(receipt)

        // Then
        coVerify { receiptRepository.createOrUpdateReceipt(receipt) }
    }
}