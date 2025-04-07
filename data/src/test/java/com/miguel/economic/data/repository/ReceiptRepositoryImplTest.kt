package com.miguel.economic.data.repository

import com.miguel.economic.data.datasource.local.ReceiptLocalDataSource
import com.miguel.economic.data.mapper.toDbModel
import com.miguel.economic.data.mapper.toModel
import com.miguel.economic.data.model.ReceiptDbModel
import com.miguel.economic.domain.model.ReceiptModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class ReceiptRepositoryImplTest {

    private val receiptLocalDataSource: ReceiptLocalDataSource = mockk()
    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    private val receiptRepository = ReceiptRepositoryImpl(
        receiptLocalDataSource = receiptLocalDataSource,
        dispatcher = dispatcher
    )

    @Test
    fun `When getReceipts is called then returned domain model from db`() = runTest(dispatcher) {
        // Given
        val dbModel = ReceiptDbModel(
            id = 1,
            photoFilename = "123file",
            amount = 2.2f,
            currencyCode = "EUR",
            created = LocalDateTime.now().toString()
        )

        coEvery { receiptLocalDataSource.getAll() } returns listOf(dbModel)

        val expected = listOf(dbModel.toModel())

        // When
        val result = receiptRepository.getReceipts()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `When getReceipt is called then return domain model from db`() = runTest(dispatcher) {
        // Given
        val dbModel = ReceiptDbModel(
            id = 1,
            photoFilename = "123file",
            amount = 2.2f,
            currencyCode = "EUR",
            created = LocalDateTime.now().toString()
        )

        coEvery { receiptLocalDataSource.get(any()) } returns dbModel

        val expected = dbModel.toModel()

        // When
        val result = receiptRepository.getReceipt(id = 1)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `When createOrUpdateReceipt is called then send model to db`() = runTest(dispatcher) {
        // Given
        coEvery { receiptLocalDataSource.insertOrUpdate(any()) } just Runs
        val model = ReceiptModel(
            photoFilename = "123file",
            amount = 2.2f,
            currencyCode = "EUR",
            createdDate = LocalDateTime.now()
        )
        val expected = model.toDbModel()

        // When
        receiptRepository.createOrUpdateReceipt(receipt = model)

        // Then
        coVerify { receiptLocalDataSource.insertOrUpdate(expected) }
    }
}