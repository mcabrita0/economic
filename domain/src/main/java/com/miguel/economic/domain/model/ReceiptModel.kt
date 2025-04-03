package com.miguel.economic.domain.model

import java.time.LocalDateTime

data class ReceiptModel(
    val photoId: String,
    val amount: Int,
    val currencyCode: String,
    val createdDate: LocalDateTime
)
