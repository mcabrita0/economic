package com.miguel.economic.receipt.model

import androidx.compose.runtime.Immutable
import com.miguel.economic.core.util.currencySymbol
import com.miguel.economic.core.util.viewFormat
import java.time.LocalDateTime

@Immutable
internal data class ReceiptViewData(
    val photoFilename: String? = null,
    val amount: String = "",
    val currencyCode: String = "",
    val createdDate: LocalDateTime? = null
) {

    val formattedAmount get() = "$amount ${currencyCode.currencySymbol.orEmpty()}"
    val formattedDate get() = createdDate?.viewFormat.orEmpty()
}