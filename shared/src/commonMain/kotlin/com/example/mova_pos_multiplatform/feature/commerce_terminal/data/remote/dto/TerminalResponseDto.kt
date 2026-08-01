package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TerminalResponseDto(
    val id: String,
    @SerialName(value = "commerce_id")
    val commerceId: String,
    val name: String,
)
