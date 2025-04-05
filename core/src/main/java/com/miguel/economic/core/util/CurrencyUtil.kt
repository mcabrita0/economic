package com.miguel.economic.core.util

import java.util.Currency

val String.currencySymbol
    get() = try {
        Currency.getInstance(this).symbol
    } catch (e: IllegalArgumentException) {
        null
    }