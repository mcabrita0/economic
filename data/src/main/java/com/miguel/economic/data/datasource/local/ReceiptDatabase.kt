package com.miguel.economic.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.miguel.economic.data.model.ReceiptDbModel

@Database(entities = [ReceiptDbModel::class], version = 1)
internal abstract class ReceiptDatabase : RoomDatabase() {

    abstract fun receiptDao(): ReceiptDao
}