package com.miguel.economic.gallery.mapper

import com.miguel.economic.core.util.CurrencyUtil
import com.miguel.economic.core.util.viewFormat
import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.gallery.model.ReceiptViewData
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class ReceiptModelExtTest {

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
            id = source.id,
            photoFilename = source.photoFilename,
            amount = CurrencyUtil.formatCurrency(source.amount, source.currencyCode),
            createdDate = source.createdDate.viewFormat
        )

        // When
        val result = source.toViewData()

        // Then
        assertEquals(expected, result)
    }
}