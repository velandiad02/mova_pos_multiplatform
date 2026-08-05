package com.example.mova_pos_multiplatform.feature.transactions.presentation.payment_channel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Nfc
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.mova_pos_multiplatform.core.designsystem.MovaRadius
import com.example.mova_pos_multiplatform.core.designsystem.MovaSize
import com.example.mova_pos_multiplatform.core.designsystem.MovaSpacing
import com.example.mova_pos_multiplatform.core.designsystem.MovaResponsive
import com.example.mova_pos_multiplatform.core.designsystem.components.MovaScreenScaffold
import com.example.mova_pos_multiplatform.core.common.extension.formatCurrency
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Money
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.PaymentChannel

@Composable
fun PaymentChannelContent(component: PaymentChannelComponent) {
    val model by component.model.subscribeAsState()
    val formattedAmount = Money(amountInMinimumUnit = model.amountInMinimumUnit * 100).formatCurrency()

    MovaResponsive { windowSize ->
        MovaScreenScaffold(windowSize = windowSize, title = "Selecciona el canal de cobro") {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MovaSpacing.lg
                )) {
                Text(
                    text = formattedAmount,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )

                ChannelOption(
                    icon = Icons.Default.Nfc,
                    label = "NFC / Sin contacto",
                    onClick = { component.onChannelSelected(PaymentChannel.NFC) },
                )
                ChannelOption(
                    icon = Icons.Default.QrCode,
                    label = "Código QR",
                    onClick = { component.onChannelSelected(PaymentChannel.QR) },
                )
                ChannelOption(
                    icon = Icons.Default.Link,
                    label = "Link de Pago",
                    onClick = { component.onChannelSelected(PaymentChannel.PAYMENT_LINK) },
                )

                TextButton(onClick = component::onCancelClicked) {
                    Text("Cancelar operación")
                }
            }
        }
    }
}

@Composable
private fun ChannelOption(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(MovaSize.buttonHeight + MovaSpacing.md)
            .clip(RoundedCornerShape(MovaRadius.medium))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick)
            .padding(horizontal = MovaSpacing.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MovaSpacing.md),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.height(MovaSize.iconLarge),
        )
        Text(text = label, style = MaterialTheme.typography.titleLarge)
    }
}