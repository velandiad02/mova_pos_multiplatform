package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.mapper

import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.dto.TerminalResponseDto
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Terminal

fun TerminalResponseDto.toDomain(): Terminal =
    Terminal(id = id, commerceId = commerceId, name = name)