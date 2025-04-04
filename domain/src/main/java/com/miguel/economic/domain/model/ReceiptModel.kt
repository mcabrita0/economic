package com.miguel.economic.domain.model

data class ReceiptModel(
    val photoFilename: String,
    val amount: Int,
    val currencyCode: String,
    val createdDate: String // TODO: localdatetime
)
