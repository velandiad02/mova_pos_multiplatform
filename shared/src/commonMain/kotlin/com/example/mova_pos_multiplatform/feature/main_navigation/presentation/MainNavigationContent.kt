package com.example.mova_pos_multiplatform.feature.main_navigation.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.mova_pos_multiplatform.core.designsystem.MovaResponsive
import com.example.mova_pos_multiplatform.feature.transactions.presentation.history.HistoryContent
import com.example.mova_pos_multiplatform.feature.transactions.presentation.pos_main.PosMainContent

@Composable
fun MainNavigationContent(component: MainNavigationComponent) {
    val stack by component.childStack.subscribeAsState()
    val selectedTab = when (stack.active.instance) {
        is MainNavigationComponent.Child.PosMain ->
            MainNavigationComponent.Tab.CASH_REGISTER

        is MainNavigationComponent.Child.History ->
            MainNavigationComponent.Tab.HISTORY
    }

    MovaResponsive { windowSize ->
        if (windowSize.isCompact) {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        MainNavigationComponent.Tab.entries.forEach { tab ->
                            NavigationBarItem(
                                selected = selectedTab == tab,
                                onClick = { component.onTabSelected(tab) },
                                icon = {
                                    Icon(
                                        imageVector = tab.icon(),
                                        contentDescription = tab.label,
                                    )
                                },
                                label = {
                                    Text(
                                        text = tab.label,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                ),
                            )
                        }
                    }
                },
            ) { paddingValues ->
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                    Children(stack = stack, modifier = Modifier.fillMaxSize()) { child ->
                        TabChildContent(child.instance)
                    }
                }
            }
        } else {
            Row(modifier = Modifier.fillMaxSize()) {
                NavigationRail {
                    MainNavigationComponent.Tab.entries.forEach { tab ->
                        NavigationRailItem(
                            selected = selectedTab == tab,
                            onClick = { component.onTabSelected(tab) },
                            icon = {
                                Icon(
                                    imageVector = tab.icon(),
                                    contentDescription = tab.label,
                                )
                            },
                            label = {
                                Text(
                                    text = tab.label,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            },
                            colors = NavigationRailItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.20f),
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                        )
                    }
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    Children(stack = stack, modifier = Modifier.fillMaxSize()) { child ->
                        TabChildContent(child.instance)
                    }
                }
            }
        }
    }
}

@Composable
private fun TabChildContent(child: MainNavigationComponent.Child) {
    when (child) {
        is MainNavigationComponent.Child.PosMain -> PosMainContent(child.component)
        is MainNavigationComponent.Child.History -> HistoryContent(child.component)
    }
}

private fun MainNavigationComponent.Tab.icon() = when (this) {
    MainNavigationComponent.Tab.CASH_REGISTER -> Icons.Default.PointOfSale
    MainNavigationComponent.Tab.HISTORY -> Icons.Default.History
}