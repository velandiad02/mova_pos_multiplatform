package com.example.mova_pos_multiplatform.feature.transactions.presentation.pos_main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update

class DefaultPosMainComponent(
    componentContext: ComponentContext,
    private val onNavigateToPaymentChannel: (amountInMinimumUnit: Long) -> Unit,
) : PosMainComponent, ComponentContext by componentContext {

    private val _model = MutableValue(PosMainComponent.Model())
    override val model: Value<PosMainComponent.Model> = _model

    override fun onAmountInputChanged(rawInput: String) {
        val digitsOnly = rawInput.filter { it.isDigit() }

        val amount = digitsOnly
            .toLongOrNull()
            ?.coerceAtMost(MAX_AMOUNT_IN_MINIMUM_UNIT)
            ?: 0L

        _model.update { it.copy(amountInMinimumUnit = amount) }
    }

    override fun onContinueClicked() {
        val state = model.value
        if (!state.canContinue) return
        onNavigateToPaymentChannel(state.amountInMinimumUnit)
    }

    private companion object {
        const val MAX_AMOUNT_IN_MINIMUM_UNIT = 999_999_999L
    }
}