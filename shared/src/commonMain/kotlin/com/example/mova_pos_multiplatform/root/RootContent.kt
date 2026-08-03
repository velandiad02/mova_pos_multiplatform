package com.example.mova_pos_multiplatform.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.mova_pos_multiplatform.feature.commerce_terminal.presentation.CommerceTerminalContent

@Composable
fun RootContent(component: RootComponent) {
    val stack by component.childStack.subscribeAsState()

    Children(stack = stack) { child ->
        when (val instance = child.instance) {
            is RootComponent.Child.CommerceTerminal -> CommerceTerminalContent(instance.component)
            // is RootComponent.Child.History -> HistoryContent(instance.component)
        }
    }
}
