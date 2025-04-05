package com.miguel.economic.domain.model

import java.time.LocalDateTime

data class ReceiptModel(
    val id: Int? = null,
    val photoFilename: String,
    val amount: Int,
    val currencyCode: String,
    val createdDate: LocalDateTime
)
