package com.example.mova_pos_multiplatform.core.common.error

class AppException(
    message: String,
    val status: ErrorStatus,
    cause: Throwable? = null
) : Exception(message, cause)