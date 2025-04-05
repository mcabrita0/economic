package com.miguel.economic.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receipt")
internal data class ReceiptDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int = ID_AUTO_GENERATE,
    @ColumnInfo(name = "filename") val photoFilename: String,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "currency_code") val currencyCode: String,
    @ColumnInfo(name = "created") val created: String
) {
    companion object {
        const val ID_AUTO_GENERATE = 0
    }
}
