package com.example.mova_pos_multiplatform.feature.commerce_terminal.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Commerce
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Terminal
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.use_case.ObserveCommercesUseCase
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.use_case.ObserveTerminalsByCommerceIdUseCase
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.use_case.RefreshCommercesUseCase
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.use_case.SyncTerminalsForCommerceUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class DefaultCommerceTerminalComponent(
    componentContext: ComponentContext,
    private val observeCommerces: ObserveCommercesUseCase,
    private val observeTerminalsByCommerceId: ObserveTerminalsByCommerceIdUseCase,
    private val refreshCommerces: RefreshCommercesUseCase,
    private val syncTerminalsForCommerce: SyncTerminalsForCommerceUseCase,
    private val onNavigateToHistory: () -> Unit,
) : CommerceTerminalComponent, ComponentContext by componentContext {

    private val scope = coroutineScope(Dispatchers.Main.immediate)

    private var observeTerminalsJob: Job? = null

    private val _model = MutableValue(CommerceTerminalComponent.Model(isRefreshing = true))
    override val model: Value<CommerceTerminalComponent.Model> = _model

    init {
        observeCommercesReactively()
        refreshCommerces(forceRefresh = false)
    }


    private fun observeCommercesReactively() {
        scope.launch {
            observeCommerces().collect { commerces ->
                _model.update { it.copy(commerces = commerces) }
            }
        }
    }

    private fun refreshCommerces(forceRefresh: Boolean) {
        _model.update { it.copy(isRefreshing = true, errorMessage = null) }
        scope.launch {
            refreshCommerces.invoke(forceRefresh = forceRefresh)
                .onSuccess {
                    _model.update { it.copy(isRefreshing = false) }
                }
                .onFailure { error ->
                    _model.update {
                        it.copy(isRefreshing = false, errorMessage = error.message ?: "Error al cargar comercios")
                    }
                }
        }
    }

    override fun onCommerceSelected(commerce: Commerce) {
        _model.update {
            it.copy(
                selectedCommerce = commerce,
                selectedTerminal = null,
                terminals = emptyList(),
                errorMessage = null,
            )
        }
        observeTerminalsReactively(commerce.id)
        syncTerminals(commerce.id)
    }

    private fun observeTerminalsReactively(commerceId: String) {
        observeTerminalsJob?.cancel()
        observeTerminalsJob = scope.launch {
            observeTerminalsByCommerceId(commerceId).collect { terminals ->
                _model.update { it.copy(terminals = terminals) }
            }
        }
    }

    private fun syncTerminals(commerceId: String) {
        _model.update { it.copy(isSyncingTerminals = true, errorMessage = null) }
        scope.launch {
            syncTerminalsForCommerce(commerceId = commerceId)
                .onSuccess {
                    _model.update { it.copy(isSyncingTerminals = false) }
                }
                .onFailure { error ->
                    _model.update {
                        it.copy(isSyncingTerminals = false, errorMessage = error.message ?: "Error al sincronizar terminales")
                    }
                }
        }
    }

    override fun onTerminalSelected(terminal: Terminal) {
        _model.update { it.copy(selectedTerminal = terminal) }
    }

    override fun onStartCashRegisterClicked() {
        if (!model.value.canStart) return
        onNavigateToHistory()
    }

    override fun onRetryClicked() {
        val state = model.value
        val commerceId = state.selectedCommerce?.id
        if (commerceId != null && state.terminals.isEmpty()) {
            syncTerminals(commerceId)
        } else {
            refreshCommerces(forceRefresh = true)
        }
    }
}
