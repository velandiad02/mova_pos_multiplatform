package com.example.mova_pos_multiplatform.feature.transactions.presentation.pos_main

import com.arkivanov.decompose.value.Value

interface PosMainComponent {

    val model: Value<Model>

    data class Model(
        val amountInMinimumUnit: Long = 0,
        val rawAmountText: String = "",
        val isOnline: Boolean = true,
    ) {
        val canContinue: Boolean
            get() = amountInMinimumUnit > 0
    }


    fun onAmountInputChanged(rawInput: String)
    fun onContinueClicked()
}
