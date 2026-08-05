package com.example.mova_pos_multiplatform.feature.transactions.presentation.payment_channel

import com.arkivanov.decompose.value.Value
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.PaymentChannel

interface PaymentChannelComponent {

    val model: Value<Model>

    data class Model(
        val amountInMinimumUnit: Long,
    )

    fun onChannelSelected(channel: PaymentChannel)
    fun onCancelClicked()
}