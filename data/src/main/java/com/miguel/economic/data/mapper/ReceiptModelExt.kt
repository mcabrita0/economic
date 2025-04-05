package com.miguel.economic.data.mapper

import android.annotation.SuppressLint
import com.miguel.economic.data.model.ReceiptDbModel
import com.miguel.economic.domain.model.ReceiptModel
import java.time.LocalDateTime

@SuppressLint("NewApi")
internal fun ReceiptDbModel.toModel() = ReceiptModel(
    id = id,
    photoFilename = photoFilename,
    amount = amount,
    currencyCode = currencyCode,
    createdDate = LocalDateTime.parse(created)
)

internal fun ReceiptModel.toDbModel() = ReceiptDbModel(
    id = id ?: ReceiptDbModel.ID_AUTO_GENERATE,
    photoFilename = photoFilename,
    amount = amount,
    currencyCode = currencyCode,
    created = createdDate.toString()
)