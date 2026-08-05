package com.example.mova_pos_multiplatform.feature.transactions.presentation.detail

import com.arkivanov.decompose.value.Value
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction

interface TransactionDetailComponent {
    val model: Value<Model>

    data class Model(
        val transaction: Transaction? = null,
        val isLoading: Boolean = false,
        val isPrinting: Boolean = false,
        val printerMessage: String? = null,
        val isPrintError: Boolean = false,
    )

    fun onPrintReceiptClicked()
    fun onBackClicked()
}