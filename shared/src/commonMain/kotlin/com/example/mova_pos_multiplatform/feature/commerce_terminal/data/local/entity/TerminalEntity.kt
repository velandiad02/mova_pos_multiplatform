package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Terminals")
data class TerminalEntity(
    @PrimaryKey val id: String,
    val commerceId: String,
    val name: String,
)