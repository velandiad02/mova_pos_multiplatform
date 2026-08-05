package com.example.mova_pos_multiplatform.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.mova_pos_multiplatform.feature.commerce_terminal.presentation.CommerceTerminalComponent
import com.example.mova_pos_multiplatform.feature.transactions.presentation.history.HistoryComponent

interface RootComponent {


    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class CommerceTerminal(val component: CommerceTerminalComponent) : Child()

//        class History(val component: HistoryComponent) : Child()
    }
}
