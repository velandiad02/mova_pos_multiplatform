package com.example.mova_pos_multiplatform.feature.transactions.presentation.pos_main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.mova_pos_multiplatform.core.common.extension.formatCurrency
import com.example.mova_pos_multiplatform.core.designsystem.MovaResponsive
import com.example.mova_pos_multiplatform.core.designsystem.MovaSpacing
import com.example.mova_pos_multiplatform.core.designsystem.components.MovaAmountField
import com.example.mova_pos_multiplatform.core.designsystem.components.MovaPrimaryButton
import com.example.mova_pos_multiplatform.core.designsystem.components.MovaScreenScaffold
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Money

@Composable
fun PosMainContent(component: PosMainComponent) {
    val model by component.model.subscribeAsState()
    val formattedAmount = Money(amountInMinimumUnit = model.amountInMinimumUnit).formatCurrency()

    MovaResponsive { windowSize ->
        MovaScreenScaffold(windowSize = windowSize, title = "Caja") {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MovaSpacing.lg),
            ) {
                Text(
                    text = formattedAmount,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                )

                MovaAmountField(
                    value = model.rawAmountText,
                    onValueChange = component::onAmountInputChanged,
                    label = "Monto a cobrar",
                    modifier = Modifier.fillMaxWidth(),
                )

                MovaPrimaryButton(
                    text = "Continuar",
                    enabled = model.canContinue,
                    onClick = component::onContinueClicked,
                )
            }
        }
    }
}