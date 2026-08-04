package com.example.mova_pos_multiplatform.core.common.utils

import androidx.sqlite.SQLiteException
import com.example.mova_pos_multiplatform.core.common.error.AppException
import com.example.mova_pos_multiplatform.core.common.error.ErrorStatus

suspend inline fun <T> runLocalCatching(crossinline block: suspend () -> T): Result<T> {
    return runCatching {
        block()
    }.recoverCatching { error ->
        val message: String = when (error) {
            is SQLiteException -> error.message ?: "Error de DB"
            else -> "Error inesperado: ${error.message}"
        }

        throw AppException(message = message, status = ErrorStatus.LOCAL_STORAGE)
    }
}