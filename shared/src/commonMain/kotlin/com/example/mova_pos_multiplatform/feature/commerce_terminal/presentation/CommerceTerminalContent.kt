package com.example.mova_pos_multiplatform.feature.commerce_terminal.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.mova_pos_multiplatform.core.designsystem.MovaResponsive
import com.example.mova_pos_multiplatform.core.designsystem.MovaSpacing
import com.example.mova_pos_multiplatform.core.designsystem.components.MovaDropdownField
import com.example.mova_pos_multiplatform.core.designsystem.components.MovaPrimaryButton
import com.example.mova_pos_multiplatform.core.designsystem.components.MovaScreenScaffold

@Composable
fun CommerceTerminalContent(component: CommerceTerminalComponent) {
    val model by component.model.subscribeAsState()

    MovaResponsive { windowSize ->
        MovaScreenScaffold(
            windowSize = windowSize,
            title = "MOVA POS",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(MovaSpacing.md)) {

                MovaDropdownField(
                    label = "Comercio",
                    options = model.commerces,
                    selected = model.selectedCommerce,
                    onSelected = component::onCommerceSelected,
                    optionLabel = { it.name },
                )

                MovaDropdownField(
                    label = "Terminal",
                    options = model.terminals,
                    selected = model.selectedTerminal,
                    onSelected = component::onTerminalSelected,
                    optionLabel = { it.name },
                )

                if (model.isSyncingTerminals) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(modifier = Modifier.height(20.dp))
                        Spacer(modifier = Modifier.height(MovaSpacing.sm))
                        Text("Sincronizando terminales...")
                    }
                }

                model.errorMessage?.let { message ->
                    Text(text = message)
                    MovaPrimaryButton(
                        text = "Reintentar",
                        onClick = component::onRetryClicked,
                    )
                }

                Spacer(modifier = Modifier.height(MovaSpacing.lg))

                MovaPrimaryButton(
                    text = "Iniciar Caja",
                    enabled = model.canStart,
                    loading = model.isRefreshing,
                    onClick = component::onStartCashRegisterClicked,
                )
            }
        }
    }
}
