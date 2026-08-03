package com.example.mova_pos_multiplatform.feature.transactions.data.remote.api

import com.example.mova_pos_multiplatform.feature.transactions.data.remote.dto.TransactionRequestDto
import com.example.mova_pos_multiplatform.feature.transactions.data.remote.dto.TransactionResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class TransactionApi(
    private val httpClient: HttpClient,
) {

    suspend fun createTransaction(transaction: TransactionRequestDto): TransactionResponseDto =
        httpClient.post(urlString = "transactions") {
            contentType(ContentType.Application.Json)
            setBody(body = transaction)
        }.body()
}