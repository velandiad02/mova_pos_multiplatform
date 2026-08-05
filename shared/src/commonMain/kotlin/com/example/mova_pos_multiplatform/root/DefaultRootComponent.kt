package com.example.mova_pos_multiplatform.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.example.mova_pos_multiplatform.feature.commerce_terminal.presentation.DefaultCommerceTerminalComponent
import com.example.mova_pos_multiplatform.feature.transactions.presentation.history.DefaultHistoryComponent
import com.example.mova_pos_multiplatform.root.RootComponent.Child.*
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
        is Config.CommerceTerminal -> CommerceTerminal(
            DefaultCommerceTerminalComponent(
                componentContext = context,
                observeCommerces = get(),
                observeTerminalsByCommerceId = get(),
                refreshCommerces = get(),
                syncTerminalsForCommerce = get(),
                onNavigateToHistory = {
                },
            )
        )
//        is Config.History -> History(
//            component = DefaultHistoryComponent(
//                componentContext = context,
//                terminalId = config.terminalId,
//                observeTransactionsUseCase = get(),
//                retrySyncTransactionsUseCase = get(),
//                onNavigateToDetail = { },
//            )
//        )
    }
    @Serializable
    private sealed class Config {
        @Serializable
        data object CommerceTerminal : Config()

//        @Serializable
//        data class History(val terminalId: String) : Config()
    }
}
