package com.miguel.economic.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miguel.economic.data.model.ReceiptDbModel

@Dao
internal interface ReceiptDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(receipt: ReceiptDbModel)

    @Query("SELECT * FROM receipt")
    suspend fun getAll(): List<ReceiptDbModel>

    @Query("SELECT * FROM receipt WHERE id = :id")
    suspend fun get(id: Int): ReceiptDbModel
}