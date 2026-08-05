package com.example.mova_pos_multiplatform.feature.transactions.presentation.payment_channel

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.PaymentChannel

class DefaultPaymentChannelComponent(
    componentContext: ComponentContext,
    amountInMinimumUnit: Long,
    private val onNavigateToProcessing: (channel: PaymentChannel) -> Unit,
    private val onCancel: () -> Unit,
) : PaymentChannelComponent, ComponentContext by componentContext {

    private val _model = MutableValue(PaymentChannelComponent.Model(amountInMinimumUnit))
    override val model: Value<PaymentChannelComponent.Model> = _model

    override fun onChannelSelected(channel: PaymentChannel) {
        onNavigateToProcessing(channel)
    }

    override fun onCancelClicked() {
        onCancel()
    }
}