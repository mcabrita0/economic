package com.miguel.economic.receipt.model

import androidx.annotation.StringRes

internal sealed class ReceiptViewEvent {

    data class TakePicture(
        val filename: String
    ) : ReceiptViewEvent()

    data object SaveAndExit : ReceiptViewEvent()

    data object Back : ReceiptViewEvent()

    data class Error(@StringRes val message: Int) : ReceiptViewEvent()
}