package com.example.mova_pos_multiplatform.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import io.ktor.http.isSuccess

fun HttpClient.addErrorInterceptor() {
    plugin(HttpSend).intercept { request ->
        try {
            val originalCall = execute(requestBuilder = request)
            val response = originalCall.response

            if (!response.status.isSuccess()) {
                throw Exception("Error de API: ${response.status.description}")
            }

            originalCall

        } catch (e: Throwable) {
            throw Exception("Error de conexión o inesperado: ${e.message}")
        }
    }
}