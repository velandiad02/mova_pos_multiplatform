package com.example.mova_pos_multiplatform.feature.transactions.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.mova_pos_multiplatform.core.designsystem.components.DetailColumn
import com.example.mova_pos_multiplatform.core.common.extension.formatCurrency
import com.example.mova_pos_multiplatform.core.common.extension.formatDate
import com.example.mova_pos_multiplatform.core.designsystem.MovaRadius
import com.example.mova_pos_multiplatform.core.designsystem.MovaSpacing
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction
import com.example.mova_pos_multiplatform.core.common.extension.getColor
import com.example.mova_pos_multiplatform.core.common.extension.getTitleToShow

@Composable
fun TransactionInfoCard(transaction: Transaction) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = MovaRadius.medium))
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .padding(all = MovaSpacing.lg),
        verticalArrangement = Arrangement.spacedBy(MovaSpacing.md)
    ) {
        Text(
            text = transaction.amount.formatCurrency(),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )

        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

        DetailColumn(label = "Referencia", value = transaction.idempotencyKey)
        DetailColumn(label = "Fecha", value = transaction.createdAt.formatDate())
        DetailColumn(label = "Método de Pago", value = transaction.channel.getTitleToShow())

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Estado",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = transaction.status.getTitleToShow(),
                style = MaterialTheme.typography.labelLarge,
                color = transaction.status.getColor(),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}