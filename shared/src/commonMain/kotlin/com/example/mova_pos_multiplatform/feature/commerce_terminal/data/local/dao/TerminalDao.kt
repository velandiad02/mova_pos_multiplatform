package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.entity.TerminalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TerminalDao {

    @Query(value = "SELECT * FROM terminals WHERE commerceId = :commerceId")
    fun observeTerminalsByCommerceId(commerceId: String): Flow<List<TerminalEntity>>

    @Query(value = "SELECT EXISTS(SELECT 1 FROM terminals WHERE commerceId = :commerceId)")
    suspend fun hasTerminals(commerceId: String): Boolean

    @Query(value = "SELECT * FROM terminals WHERE id = :id")
    suspend fun getTerminalById(id: String): TerminalEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTerminals(terminals: List<TerminalEntity>)

    @Query(value = "DELETE FROM terminals WHERE commerceId = :commerceId")
    suspend fun deleteTerminalsByCommerce(commerceId: String)

    @Query(value = "DELETE FROM terminals")
    suspend fun deleteAllTerminals()

    @Transaction
    suspend fun replaceTerminalsByCommerce(commerceId: String, terminals: List<TerminalEntity>) {
        deleteTerminalsByCommerce(commerceId = commerceId)
        saveTerminals(terminals = terminals)
    }
}