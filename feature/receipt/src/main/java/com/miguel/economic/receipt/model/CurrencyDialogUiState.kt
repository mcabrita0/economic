package com.miguel.economic.receipt.model

import androidx.compose.runtime.Immutable

@Immutable
sealed class CurrencyDialogUiState {

    @Immutable
    data object Hide : CurrencyDialogUiState()

    @Immutable
    data class Show(
        val currencyCodes: List<String>,
        val currencyCode: String,
        val amount: String,
    ) : CurrencyDialogUiState()

}
