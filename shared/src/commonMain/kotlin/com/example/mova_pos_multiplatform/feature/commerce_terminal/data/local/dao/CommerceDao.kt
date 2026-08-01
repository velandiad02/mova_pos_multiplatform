package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.entity.CommerceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CommerceDao {

    @Query(value = "SELECT * FROM commerces")
    fun observeCommerces(): Flow<List<CommerceEntity>>

    @Query(value = "SELECT EXISTS(SELECT 1 FROM commerces)")
    suspend fun hasCommerces(): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCommerces(commerces: List<CommerceEntity>)

    @Query(value = "DELETE FROM commerces")
    suspend fun deleteAllCommerces()

    @Transaction
    suspend fun replaceAll(commerces: List<CommerceEntity>) {
        deleteAllCommerces()
        saveCommerces(commerces = commerces)
    }
}