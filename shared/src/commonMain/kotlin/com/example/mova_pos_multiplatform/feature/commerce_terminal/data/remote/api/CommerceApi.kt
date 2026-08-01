package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.api

import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.dto.CommerceResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CommerceApi(private val httpClient: HttpClient) {

    suspend fun fetchCommerces(): List<CommerceResponseDto> =
        httpClient.get(urlString = "commerces").body()
}