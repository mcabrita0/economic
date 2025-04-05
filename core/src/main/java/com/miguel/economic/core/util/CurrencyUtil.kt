package com.miguel.economic.core.util

import android.annotation.SuppressLint
import java.util.Currency

object CurrencyUtil {

    @SuppressLint("NewApi")
    fun allCurrencyCodes(): List<String> {
        return Currency.getAvailableCurrencies()
            .map { it.currencyCode }
            .let { listOf("EUR") + it }
            .distinct()
    }
}


val String.currency
    get() = try {
        Currency.getInstance(this)
    } catch (e: IllegalArgumentException) {
        null
    }

val String.currencySymbol
    get() = currency?.symbol