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

class GetReceiptsUseCaseTest {

    private val receiptRepository: ReceiptRepository = mockk()
    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    private val getReceiptsUseCase = GetReceiptsUseCase(
        receiptRepository = receiptRepository,
        dispatcher = dispatcher
    )

    @Test
    fun `When invoked then return receipts from repository sorted`() = runTest(dispatcher) {
        // Given
        val receiptA = ReceiptModel(
            id = 1,
            photoFilename = "file123",
            amount = 12.3f,
            currencyCode = "EUR",
            createdDate = LocalDateTime.MIN
        )
        val receiptB = ReceiptModel(
            id = 1,
            photoFilename = "file222",
            amount = 14.3f,
            currencyCode = "EUR",
            createdDate = LocalDateTime.MAX
        )
        coEvery { receiptRepository.getReceipts() } returns listOf(receiptA, receiptB)

        val expected = listOf(receiptB, receiptA)

        // When
        val result = getReceiptsUseCase()

        // Then
        assertEquals(expected, result)
    }
}