package com.miguel.economic.receipt.mapper

import com.miguel.economic.core.util.CurrencyUtil
import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.receipt.model.CurrencyDialogUiState
import com.miguel.economic.receipt.model.ReceiptViewData
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.LocalDateTime

class ReceiptModelTest {

    @Test
    fun `ReceiptModel mapping to ViewData`() {
        // Given
        val source = ReceiptModel(
            id = 1,
            photoFilename = "filename",
            amount = 1.25f,
            currencyCode = "EUR",
            createdDate = LocalDateTime.now()
        )

        val expected = ReceiptViewData(
            photoFilename = source.photoFilename,
            amount = source.amount.toString(),
            currencyCode = source.currencyCode,
            createdDate = source.createdDate
        )

        // When
        val result = source.toViewData()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `ReceiptViewData mapping to ReceiptModel returns null if any field is null or empty`() {
        // Given
        val source = ReceiptViewData(
            photoFilename = "filename",
            amount = 1.25f.toString(),
            currencyCode = "EUR",
            createdDate = LocalDateTime.now()
        )
        val sources = listOf(
            source.copy(photoFilename = null),
            source.copy(amount = ""),
            source.copy(currencyCode = ""),
            source.copy(createdDate = null)
        )

        // When
        val results = sources.map {
            it.toModel(id = 1)
        }

        // Then
        for (result in results) {
            assertNull(result)
        }
    }

    @Test
    fun `ReceiptViewData mapping to ReceiptModel returns null if amount is not a float`() {
        // Given
        val source = ReceiptViewData(
            photoFilename = "filename",
            amount = "1.25a",
            currencyCode = "EUR",
            createdDate = LocalDateTime.now()
        )

        // When
        val result = source.toModel(id = 1)

        // Then
        assertNull(result)
    }


    @Test
    fun `ReceiptViewData mapping to CurrencyDialogUiState`() {
        // Given
        mockkObject(CurrencyUtil)
        every { CurrencyUtil.allCurrencyCodes() } returns listOf("EUR", "DKK")
        val source = ReceiptViewData(
            photoFilename = "filename",
            amount = "1.25a",
            currencyCode = "EUR",
            createdDate = LocalDateTime.now()
        )

        val expected = CurrencyDialogUiState.Show(
            currencyCodes = CurrencyUtil.allCurrencyCodes(),
            currencyCode = source.currencyCode,
            amount = source.amount
        )

        // When
        val result = source.toDialogUiState()

        // Then
        assertEquals(expected, result)

        unmockkObject(CurrencyUtil)
    }
}