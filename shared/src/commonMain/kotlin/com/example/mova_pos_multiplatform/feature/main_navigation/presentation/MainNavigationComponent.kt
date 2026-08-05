package com.example.mova_pos_multiplatform.feature.main_navigation.presentation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.mova_pos_multiplatform.feature.transactions.presentation.pos_main.PosMainComponent

interface MainNavigationComponent {

    val childStack: Value<ChildStack<*, Child>>
    val activeTab: Tab

    fun onTabSelected(tab: Tab)

    enum class Tab(val label: String) {
        CASH_REGISTER("Caja"),
        HISTORY("Historial"),
    }

    sealed class Child {
        class PosMain(val component: PosMainComponent) : Child()
        data object HistoryPlaceholder : Child()
    }
}