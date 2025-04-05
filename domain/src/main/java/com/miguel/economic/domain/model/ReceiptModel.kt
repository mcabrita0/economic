package com.miguel.economic.domain.model

import java.time.LocalDateTime

data class ReceiptModel(
    val id: Int? = null,
    val photoFilename: String,
    val amount: Float,
    val currencyCode: String,
    val createdDate: LocalDateTime
)
