package com.miguel.economic.gallery.mapper

import com.miguel.economic.core.util.CurrencyUtil
import com.miguel.economic.core.util.viewFormat
import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.gallery.model.ReceiptViewData

internal fun ReceiptModel.toViewData() = ReceiptViewData(
    id = id,
    photoFilename = photoFilename,
    amount = CurrencyUtil.formatCurrency(amount, currencyCode),
    createdDate = createdDate.viewFormat
)