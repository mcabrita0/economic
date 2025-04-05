package com.miguel.economic.gallery.mapper

import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.gallery.model.ReceiptViewData
import java.util.Currency

internal fun ReceiptModel.toViewData() = ReceiptViewData(
    id = id,
    photoFilename = photoFilename,
    amount = "$amount ${Currency.getInstance(currencyCode).symbol}",
    createdDate = createdDate
)