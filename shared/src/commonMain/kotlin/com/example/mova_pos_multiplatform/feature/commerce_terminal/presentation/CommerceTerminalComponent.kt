package com.example.mova_pos_multiplatform.feature.commerce_terminal.presentation

import com.arkivanov.decompose.value.Value
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Commerce
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Terminal

interface CommerceTerminalComponent {

    val model: Value<Model>

    data class Model(
        val commerces: List<Commerce> = emptyList(),
        val terminals: List<Terminal> = emptyList(),
        val selectedCommerce: Commerce? = null,
        val selectedTerminal: Terminal? = null,
        val isRefreshing: Boolean = false,
        val isSyncingTerminals: Boolean = false,
        val errorMessage: String? = null,
    ) {
        val canStart: Boolean
            get() = selectedCommerce != null && selectedTerminal != null && !isSyncingTerminals
    }

    fun onCommerceSelected(commerce: Commerce)
    fun onTerminalSelected(terminal: Terminal)
    fun onStartCashRegisterClicked()
    fun onRetryClicked()
}
