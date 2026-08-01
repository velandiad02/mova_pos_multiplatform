package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commerces")
data class CommerceEntity(
    @PrimaryKey val id: String,
    val name: String,
    val nit: String?,
)