package com.miguel.economic.data.datasource.local

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miguel.economic.data.model.ReceiptDbModel

internal class ReceiptLocalDataSource(
    private val context: Context
) {

    private val database by lazy {
        Room.databaseBuilder(
            context,
            ReceiptDatabase::class.java,
            "receipt"
        ).build()
    }

    fun insertOrUpdate(receipt: ReceiptDbModel) {
        database.receiptDao().insertOrUpdate(receipt)
    }

    fun getAll(): List<ReceiptDbModel> {
        return database.receiptDao().getAll()
    }

    fun get(id: Int): ReceiptDbModel {
        return database.receiptDao().get(id)
    }
}

@Database(entities = [ReceiptDbModel::class], version = 1)
private abstract class ReceiptDatabase : RoomDatabase() {

    abstract fun receiptDao(): ReceiptDao
}

@Dao
private interface ReceiptDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(receipt: ReceiptDbModel)

    @Query("SELECT * FROM receipt")
    fun getAll(): List<ReceiptDbModel>

    @Query("SELECT * FROM receipt WHERE id = :id")
    fun get(id: Int): ReceiptDbModel
}