package com.miguel.economic.receipt.mapper

import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.receipt.model.ReceiptViewData

internal fun ReceiptModel.toViewData() = ReceiptViewData(
    photoFilename = photoFilename,
    amount = amount,
    currencyCode = currencyCode,
    createdDate = createdDate
)