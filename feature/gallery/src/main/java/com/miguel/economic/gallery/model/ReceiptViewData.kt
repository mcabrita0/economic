package com.miguel.economic.gallery.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class ReceiptViewData(
    val id: Int?,
    val photoFilename: String,
    val amount: String,
    val createdDate: String
)