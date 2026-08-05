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
import com.example.mova_pos_multiplatform.core.designsystem.components.DetailRow
import com.example.mova_pos_multiplatform.core.common.extension.formatCurrency
import com.example.mova_pos_multiplatform.core.common.extension.formatDate
import com.example.mova_pos_multiplatform.core.designsystem.MovaRadius
import com.example.mova_pos_multiplatform.core.designsystem.MovaSpacing
import com.example.mova_pos_multiplatform.core.designsystem.MovaStatusColors
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.TransactionStatus

@Composable
fun TransactionInfoCard(transaction: Transaction) {
    val statusColor = when (transaction.status) {
        TransactionStatus.APPROVED -> MovaStatusColors.completed
        TransactionStatus.REJECTED, TransactionStatus.FAILED -> MovaStatusColors.failed
        TransactionStatus.PENDING_SYNC -> MovaStatusColors.pendingSync
    }

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

        DetailRow(label = "Referencia", value = transaction.idempotencyKey)
        DetailRow(label = "Fecha", value = transaction.createdAt.formatDate())
        DetailRow(label = "Método de Pago", value = transaction.channel.name)

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
                text = transaction.status.name,
                style = MaterialTheme.typography.labelLarge,
                color = statusColor,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}