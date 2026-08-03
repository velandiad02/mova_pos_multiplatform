package com.example.mova_pos_multiplatform.core.network

import com.example.mova_pos_multiplatform.core.common.error.AppException
import com.example.mova_pos_multiplatform.core.common.error.ErrorStatus
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import java.io.IOException

fun HttpClient.addErrorInterceptor() {
    plugin(HttpSend).intercept { request ->
        try {
            val originalCall = execute(requestBuilder = request)
            val response = originalCall.response

            when (val statusCode = response.status.value) {
                in 200..299 -> return@intercept originalCall

                in 400..499 -> throw AppException(
                    message = "Error de API: ${response.status.description}",
                    status = ErrorStatus.NON_RETRYABLE,
                )

                in 500..599 -> throw AppException(
                    message = "Error de servidor: ${response.status.description}",
                    status = ErrorStatus.RETRYABLE,
                )

                else -> throw AppException(
                    message = "Error HTTP inesperado: $statusCode",
                    status = ErrorStatus.UNKNOWN,
                )
            }
        } catch (e: Throwable) {
            if (e is AppException) throw e

            val (message, status) = when (e) {
                is IOException -> "Error de conexión a internet" to ErrorStatus.RETRYABLE
                else -> "Error inesperado: ${e.message}" to ErrorStatus.UNKNOWN
            }

            throw AppException(message, status, e)
        }
    }
}