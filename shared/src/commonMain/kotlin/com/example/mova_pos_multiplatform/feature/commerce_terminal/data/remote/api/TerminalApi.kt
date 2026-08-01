package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.api

import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.dto.TerminalResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class TerminalApi(private val httpClient: HttpClient) {

    suspend fun fetchTerminalsByCommerceId(commerceId: String): List<TerminalResponseDto> =
        httpClient.get(urlString = "terminals/$commerceId").body()
}