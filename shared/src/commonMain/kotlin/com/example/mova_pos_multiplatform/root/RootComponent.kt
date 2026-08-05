package com.example.mova_pos_multiplatform.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.mova_pos_multiplatform.feature.commerce_terminal.presentation.CommerceTerminalComponent
import com.example.mova_pos_multiplatform.feature.main_navigation.presentation.MainNavigationComponent
import com.example.mova_pos_multiplatform.feature.transactions.presentation.payment_channel.PaymentChannelComponent
import com.example.mova_pos_multiplatform.feature.transactions.presentation.processing.ProcessingComponent

interface RootComponent {


    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class CommerceTerminal(val component: CommerceTerminalComponent) : Child()
        class MainNavigation(val component: MainNavigationComponent) : Child()
        class PaymentChannel(val component: PaymentChannelComponent) : Child()
        class Processing(val component: ProcessingComponent) : Child()
    }
}
