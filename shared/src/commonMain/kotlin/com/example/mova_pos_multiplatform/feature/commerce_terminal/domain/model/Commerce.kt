package com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model

data class Commerce(
    val id: String,
    val name: String,
    val taxId: String? = null,
)