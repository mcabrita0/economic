package com.miguel.economic.receipt.mapper

import android.annotation.SuppressLint
import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.receipt.model.ReceiptViewData

internal fun ReceiptModel.toViewData() = ReceiptViewData(
    photoFilename = photoFilename,
    amount = amount,
    currencyCode = currencyCode,
    createdDate = createdDate
)

@SuppressLint("NewApi")
internal fun ReceiptViewData.toModel(id: Int?): ReceiptModel? {
    return ReceiptModel(
        id = id,
        photoFilename = photoFilename ?: return null,
        amount = amount ?: 0, //?: return null,
        currencyCode = currencyCode.orEmpty(), //?: return null,
        createdDate = createdDate ?: return null
    )
}