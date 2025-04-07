package com.miguel.economic.data.datasource.local

import com.miguel.economic.data.model.ReceiptDbModel
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

class ReceiptLocalDataSourceTest {

    private val receiptDatabase: ReceiptDatabase = mockk()
    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    private val receiptLocalDataSource = ReceiptLocalDataSource(
        receiptDatabase = receiptDatabase,
        dispatcher = dispatcher
    )

    @Test
    fun `When insertOrUpdate is called then insert value into db`() = runTest(dispatcher) {
        // Given
        val model = ReceiptDbModel(
            photoFilename = "file",
            amount = 1.2f,
            currencyCode = "EUR",
            created = LocalDateTime.now().toString()
        )
        coEvery { receiptDatabase.receiptDao().insertOrUpdate(any()) } just Runs

        // When
        receiptLocalDataSource.insertOrUpdate(model)

        // Then
        coVerify { receiptDatabase.receiptDao().insertOrUpdate(model) }
    }

    @Test
    fun `When getAll is called then return values from db`() = runTest(dispatcher) {
        // Given
        val model = ReceiptDbModel(
            photoFilename = "file",
            amount = 1.2f,
            currencyCode = "EUR",
            created = LocalDateTime.now().toString()
        )
        coEvery { receiptDatabase.receiptDao().getAll() } returns listOf(model)

        // When
        val result = receiptLocalDataSource.getAll()

        // Then
        assertEquals(listOf(model), result)
    }

    @Test
    fun `When get is called then return value from db`() = runTest(dispatcher) {
        // Given
        val model = ReceiptDbModel(
            id = 1,
            photoFilename = "file",
            amount = 1.2f,
            currencyCode = "EUR",
            created = LocalDateTime.now().toString()
        )
        coEvery { receiptDatabase.receiptDao().get(any()) } returns model

        // When
        val result = receiptLocalDataSource.get(id = 1)

        // Then
        assertEquals(model, result)
    }
}