package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.mapper

import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.entity.TerminalEntity
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Terminal

fun TerminalEntity.toDomain(): Terminal = Terminal(id = id, commerceId = commerceId, name = name)

fun Terminal.toEntity(): TerminalEntity =
    TerminalEntity(id = id, commerceId = commerceId, name = name)