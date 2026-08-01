package com.example.mova_pos_multiplatform.core.common

import androidx.sqlite.SQLiteException

suspend inline fun <T> runLocalCatching(crossinline block: suspend () -> T): Result<T> {
    return runCatching {
        block()
    }.recoverCatching { error ->
        val message: String = when (error) {
            is SQLiteException -> error.message ?: "Error de DB"
            else -> "Error inesperado: ${error.message}"
        }

        throw Exception(message)
    }
}