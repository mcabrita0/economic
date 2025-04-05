package com.miguel.economic.receipt.mapper

import android.annotation.SuppressLint
import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.receipt.model.ReceiptViewData

internal fun ReceiptModel.toViewData() = ReceiptViewData(
    photoFilename = photoFilename,
    amount = amount.toString(),
    currencyCode = currencyCode,
    createdDate = createdDate
)

@SuppressLint("NewApi")
internal fun ReceiptViewData.toModel(id: Int?): ReceiptModel? {
    if (photoFilename == null || amount.isEmpty() || currencyCode.isEmpty() || createdDate == null) {
        return null
    }

    return ReceiptModel(
        id = id,
        photoFilename = photoFilename,
        amount = amount.toFloatOrNull() ?: return null,
        currencyCode = currencyCode,
        createdDate = createdDate
    )
}