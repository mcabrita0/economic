package com.miguel.economic.domain.model

data class ReceiptModel(
    val id: Int = ID_CREATE,
    val photoFilename: String,
    val amount: Int,
    val currencyCode: String,
    val createdDate: String // TODO: localdatetime
) {

    companion object {
        const val ID_CREATE = -1
    }
}
