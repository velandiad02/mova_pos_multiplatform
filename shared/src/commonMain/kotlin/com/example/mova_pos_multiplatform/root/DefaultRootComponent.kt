package com.example.mova_pos_multiplatform.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popWhile
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.example.mova_pos_multiplatform.feature.commerce_terminal.presentation.DefaultCommerceTerminalComponent
import com.example.mova_pos_multiplatform.feature.main_navigation.presentation.DefaultMainNavigationComponent
import com.example.mova_pos_multiplatform.feature.transactions.presentation.payment_channel.DefaultPaymentChannelComponent
import com.example.mova_pos_multiplatform.feature.transactions.presentation.processing.DefaultProcessingComponent
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext, KoinComponent {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.CommerceTerminal,
            handleBackButton = true,
            childFactory = ::createChild,
        )

    private fun createChild(
        config: Config,
        context: ComponentContext,
    ): RootComponent.Child = when (config) {
        is Config.CommerceTerminal -> RootComponent.Child.CommerceTerminal(
            DefaultCommerceTerminalComponent(
                componentContext = context,
                observeCommerces = get(),
                observeTerminalsByCommerceId = get(),
                refreshCommerces = get(),
                syncTerminalsForCommerce = get(),
                // El terminalId sale directo de acá, sin repositorio de por medio.
                onNavigateToMainNavigation = { terminalId ->
                    navigation.pushNew(Config.MainNavigation(terminalId = terminalId))
                },
            )
        )

        is Config.MainNavigation -> RootComponent.Child.MainNavigation(
            DefaultMainNavigationComponent(
                componentContext = context,
                terminalId = config.terminalId,
                onNavigateToPaymentChannel = { amountInMinimumUnit ->
                    navigation.pushNew(
                        Config.PaymentChannel(
                            terminalId = config.terminalId,
                            amountInMinimumUnit = amountInMinimumUnit,
                        )
                    )
                },
            )
        )

        is Config.PaymentChannel -> RootComponent.Child.PaymentChannel(
            DefaultPaymentChannelComponent(
                componentContext = context,
                amountInMinimumUnit = config.amountInMinimumUnit,
                onNavigateToProcessing = { channel ->
                    navigation.pushNew(
                        Config.Processing(
                            terminalId = config.terminalId,
                            amountInMinimumUnit = config.amountInMinimumUnit,
                            channel = channel,
                        )
                    )
                },
                onCancel = { navigation.pop() },
            )
        )

        is Config.Processing -> RootComponent.Child.Processing(
            DefaultProcessingComponent(
                componentContext = context,
                terminalId = config.terminalId,
                amountInMinimumUnit = config.amountInMinimumUnit,
                channel = config.channel,
                readTransactionChannel = get(),
                createTransaction = get(),
                onNavigateBackToChannelSelection = { navigation.pop() },
                onFinishToMainNavigation = {
                    navigation.popWhile { it !is Config.MainNavigation }
                },
            )
        )
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object CommerceTerminal : Config()

        @Serializable
        data class MainNavigation(val terminalId: String) : Config()

        @Serializable
        data class PaymentChannel(val terminalId: String, val amountInMinimumUnit: Long) : Config()

        @Serializable
        data class Processing(
            val terminalId: String,
            val amountInMinimumUnit: Long,
            val channel: com.example.mova_pos_multiplatform.feature.transactions.domain.model.PaymentChannel,
        ) : Config()
    }
}