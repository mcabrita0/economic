package com.miguel.economic.receipt.model

import androidx.compose.runtime.Immutable
import com.miguel.economic.core.util.viewFormat
import java.time.LocalDateTime

@Immutable
internal data class ReceiptViewData(
    val photoFilename: String? = null,
    val amount: Int? = null,
    val currencyCode: String? = null,
    val createdDate: LocalDateTime? = null
) {

    val formattedDate get() = createdDate?.viewFormat.orEmpty()
}