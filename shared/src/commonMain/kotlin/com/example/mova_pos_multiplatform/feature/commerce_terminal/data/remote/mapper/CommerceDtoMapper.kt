package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.mapper

import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.dto.CommerceResponseDto
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Commerce

fun CommerceResponseDto.toDomain(): Commerce = Commerce(id = id, name = name, nit = nit)