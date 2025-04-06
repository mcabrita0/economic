package com.miguel.economic.domain.usecase

import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.domain.repository.ReceiptRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class GetReceiptUseCaseTest {

    private val receiptRepository: ReceiptRepository = mockk()
    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    private val getReceiptUseCase = GetReceiptUseCase(
        receiptRepository = receiptRepository,
        dispatcher = dispatcher
    )

    @Test
    fun `When invoked then return value from repository`() = runTest(dispatcher) {
        // Given
        val receipt = ReceiptModel(
            id = 1,
            photoFilename = "file3",
            amount = 5.3f,
            currencyCode = "EUR",
            createdDate = LocalDateTime.now()
        )
        coEvery { receiptRepository.getReceipt(any()) } returns receipt

        // When
        val result = getReceiptUseCase(id = 1)

        // Then
        assertEquals(receipt, result)
    }
}