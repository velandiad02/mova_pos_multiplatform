package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.mapper

import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.entity.CommerceEntity
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Commerce

fun CommerceEntity.toDomain(): Commerce = Commerce(id = id, name = name, nit = nit)

fun Commerce.toEntity(): CommerceEntity = CommerceEntity(id = id, name = name, nit = nit)