package com.miguel.economic.data.mapper

import com.miguel.economic.data.model.ReceiptDbModel
import com.miguel.economic.domain.model.ReceiptModel
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class ReceiptModelTest {

    @Test
    fun `Test ReceiptDbModel mapping to ReceiptModel`() {
        // Given
        val dbModel = ReceiptDbModel(
            id = 2,
            photoFilename = "photo123",
            amount = 1.2f,
            currencyCode = "EUR",
            created = LocalDateTime.now().toString()
        )
        val expected = ReceiptModel(
            id = dbModel.id,
            photoFilename = dbModel.photoFilename,
            amount = dbModel.amount,
            currencyCode = dbModel.currencyCode,
            createdDate = LocalDateTime.parse(dbModel.created)
        )

        // When
        val result = dbModel.toModel()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `Test ReceiptModel mapping to ReceiptDbModel`() {
        // Given
        val model = ReceiptModel(
            id = null,
            photoFilename = "photo123",
            amount = 1.2f,
            currencyCode = "EUR",
            createdDate = LocalDateTime.now()
        )
        val expected = ReceiptDbModel(
            id = ReceiptDbModel.ID_AUTO_GENERATE,
            photoFilename = model.photoFilename,
            amount = model.amount,
            currencyCode = model.currencyCode,
            created = model.createdDate.toString()
        )

        // When
        val result = model.toDbModel()

        // Then
        assertEquals(expected, result)
    }
}