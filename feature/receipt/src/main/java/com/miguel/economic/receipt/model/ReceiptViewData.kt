package com.miguel.economic.receipt.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class ReceiptViewData(
    val photoFilename: String? = null,
    val amount: Int? = null,
    val currencyCode: String? = null,
    val createdDate: String? = null
)