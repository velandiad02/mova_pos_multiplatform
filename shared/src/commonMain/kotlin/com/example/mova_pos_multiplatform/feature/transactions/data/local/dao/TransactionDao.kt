package com.example.mova_pos_multiplatform.feature.transactions.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mova_pos_multiplatform.feature.transactions.data.local.entity.TransactionEntity
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.TransactionStatus

@Dao
interface TransactionDao {

    @Query(value = "SELECT * FROM transactions WHERE status = :status AND terminalId = :terminalId")
    suspend fun getTransactionsBySyncStatus(status: TransactionStatus, terminalId: String): List<TransactionEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveTransaction(transaction: TransactionEntity)

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

    @Update
    suspend fun updateTransactions(transactions: List<TransactionEntity>)
}