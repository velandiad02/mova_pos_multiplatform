package com.example.mova_pos_multiplatform.feature.transactions.presentation.processing

import com.arkivanov.decompose.value.Value

interface ProcessingComponent {

    val model: Value<Model>

    sealed interface Model {
        data object ReadingChannel : Model
        data object CreatingTransaction : Model
        data class Success(val message: String) : Model
        data class Error(val message: String) : Model
    }

    fun onRetryClicked()
    fun onCancelClicked()
}
