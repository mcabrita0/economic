package com.miguel.economic.gallery.mapper

import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.gallery.model.GalleryItemViewData
import java.util.Currency

internal fun ReceiptModel.toViewData() = GalleryItemViewData(
    photoFilename = photoFilename,
    amount = "$amount ${Currency.getInstance(currencyCode).symbol}",
    createdDate = createdDate.toString()
)