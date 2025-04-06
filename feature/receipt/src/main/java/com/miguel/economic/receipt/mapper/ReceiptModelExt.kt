package com.miguel.economic.receipt.mapper

import com.miguel.economic.core.util.CurrencyUtil
import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.receipt.model.CurrencyDialogUiState
import com.miguel.economic.receipt.model.ReceiptViewData

internal fun ReceiptModel.toViewData() = ReceiptViewData(
    photoFilename = photoFilename,
    amount = amount.toString(),
    currencyCode = currencyCode,
    createdDate = createdDate
)

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

internal fun ReceiptViewData.toDialogUiState() = CurrencyDialogUiState.Show(
    currencyCodes = CurrencyUtil.allCurrencyCodes(),
    currencyCode = currencyCode,
    amount = amount
)