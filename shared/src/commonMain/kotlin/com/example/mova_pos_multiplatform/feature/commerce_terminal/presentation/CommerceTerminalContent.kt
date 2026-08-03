package com.example.mova_pos_multiplatform.feature.commerce_terminal.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
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
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(MovaSpacing.md
                )) {

                    Text(
                        text = "MOVA POS",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(MovaSpacing.lg))

                    MovaDropdownField(
                        label = "Comercio",
                        options = model.commerces,
                        selected = model.selectedCommerce,
                        onSelected = component::onCommerceSelected,
                        optionLabel = { it.name },
                        isLoading = model.isRefreshing,
                        placeholder = "Selecciona un comercio",
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (model.selectedCommerce == null && model.commerces.isNotEmpty()) {
                        Text(
                            text = "Selecciona un comercio para ver sus terminales",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    if (model.selectedCommerce != null) {
                        MovaDropdownField(
                            label = "Terminal",
                            options = model.terminals,
                            selected = model.selectedTerminal,
                            onSelected = component::onTerminalSelected,
                            optionLabel = { it.name },
                            isLoading = model.isSyncingTerminals,
                            placeholder = "Selecciona un terminal",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    model.errorMessage?.let { message ->
                        Text(text = message)
                        MovaPrimaryButton(
                            text = "Reintentar",
                            onClick = component::onRetryClicked,
                        )
                    }

                    MovaPrimaryButton(
                        text = "Iniciar",
                        enabled = model.canStart,
                        onClick = component::onStartCashRegisterClicked,
                    )
                }
            }
        }
    }
}
