package com.miguel.economic.receipt.model

import androidx.compose.runtime.Immutable

@Immutable
internal sealed class ReceiptUiState {

    @Immutable
    data object Loading : ReceiptUiState()

    @Immutable
    data class Success(
        val receipt: ReceiptViewData
    ) : ReceiptUiState()
}
