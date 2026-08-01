package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CommerceResponseDto(
    val id: String,
    val name: String,
    val nit: String? = null,
)