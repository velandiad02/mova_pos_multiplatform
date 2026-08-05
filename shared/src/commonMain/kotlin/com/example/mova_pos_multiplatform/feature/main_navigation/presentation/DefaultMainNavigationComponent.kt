package com.example.mova_pos_multiplatform.feature.main_navigation.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction
import com.example.mova_pos_multiplatform.feature.transactions.domain.use_case.GetTransactionsByTerminalUseCase
import com.example.mova_pos_multiplatform.feature.transactions.domain.use_case.RetryPendingTransactionsUseCase
import com.example.mova_pos_multiplatform.feature.transactions.presentation.history.DefaultHistoryComponent
import com.example.mova_pos_multiplatform.feature.transactions.presentation.pos_main.DefaultPosMainComponent
import kotlinx.serialization.Serializable

class DefaultMainNavigationComponent(
    componentContext: ComponentContext,
    private val terminalId: String,
    private val getTransactionsByTerminalUseCase: GetTransactionsByTerminalUseCase,
    private val retrySyncTransactionsUseCase: RetryPendingTransactionsUseCase,
    private val onNavigateToPaymentChannel: (amountInMinimumUnit: Long) -> Unit,
    private val onNavigateToTransactionDetail: (Transaction) -> Unit,
) : MainNavigationComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<TabConfig>()

    override val childStack: Value<ChildStack<*, MainNavigationComponent.Child>> =
        childStack(
            source = navigation,
            serializer = TabConfig.serializer(),
            initialConfiguration = TabConfig.CashRegister,
            handleBackButton = false,
            childFactory = ::createChild,
        )

    override val activeTab: MainNavigationComponent.Tab
        get() = when (childStack.value.active.configuration) {
            is TabConfig.CashRegister -> MainNavigationComponent.Tab.CASH_REGISTER
            is TabConfig.History -> MainNavigationComponent.Tab.HISTORY
            else -> {}
        } as MainNavigationComponent.Tab

    override fun onTabSelected(tab: MainNavigationComponent.Tab) {
        val config = when (tab) {
            MainNavigationComponent.Tab.CASH_REGISTER -> TabConfig.CashRegister
            MainNavigationComponent.Tab.HISTORY -> TabConfig.History
        }
        navigation.bringToFront(config)
    }

    private fun createChild(
        config: TabConfig,
        context: ComponentContext,
    ): MainNavigationComponent.Child = when (config) {
        is TabConfig.CashRegister -> MainNavigationComponent.Child.PosMain(
            DefaultPosMainComponent(
                componentContext = context,
                onNavigateToPaymentChannel = onNavigateToPaymentChannel,
            )
        )
        is TabConfig.History -> MainNavigationComponent.Child.History(
            component = DefaultHistoryComponent(
                componentContext = context,
                terminalId = terminalId,
                observeTransactionsUseCase = getTransactionsByTerminalUseCase,
                retrySyncTransactionsUseCase = retrySyncTransactionsUseCase,
                onNavigateToDetail = { transaction ->
                    onNavigateToTransactionDetail(transaction)
                },
            )
        )
    }

    @Serializable
    private sealed class TabConfig {
        @Serializable
        data object CashRegister : TabConfig()

        @Serializable
        data object History : TabConfig()
    }
}