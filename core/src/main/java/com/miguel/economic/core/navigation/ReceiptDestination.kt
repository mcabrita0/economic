package com.miguel.economic.core.navigation

import kotlinx.serialization.Serializable

@Serializable
data class ReceiptDestination(val receiptId: Int?) : NavigationDestination()