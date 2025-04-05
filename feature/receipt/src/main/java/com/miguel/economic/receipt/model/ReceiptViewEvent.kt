package com.miguel.economic.receipt.model

internal sealed class ReceiptViewEvent {

    data class TakePicture(
        val filename: String
    ) : ReceiptViewEvent()

    data object SaveAndExit : ReceiptViewEvent()

    data object Back : ReceiptViewEvent()

    data class Error(val message: String) : ReceiptViewEvent()
}