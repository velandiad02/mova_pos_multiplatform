package com.example.mova_pos_multiplatform.feature.transactions.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.mova_pos_multiplatform.core.common.extension.formatCurrency
import com.example.mova_pos_multiplatform.core.common.extension.formatDate
import com.example.mova_pos_multiplatform.core.designsystem.MovaRadius
import com.example.mova_pos_multiplatform.core.designsystem.MovaSpacing
import com.example.mova_pos_multiplatform.core.common.extension.getColor
import com.example.mova_pos_multiplatform.core.common.extension.getTitleToShow
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction

@Composable
fun TransactionItem(transaction: Transaction, onItemClicked: () -> Unit) {
    Card(
        onClick = onItemClicked,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(size = MovaRadius.medium),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(all = MovaSpacing.md),
            verticalArrangement = Arrangement.spacedBy(space = MovaSpacing.xs),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = transaction.amount.formatCurrency(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = transaction.status.getTitleToShow(),
                    style = MaterialTheme.typography.labelLarge,
                    color = transaction.status.getColor(),
                    fontWeight = FontWeight.Bold,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = transaction.createdAt.formatDate(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "Canal: ${transaction.channel.getTitleToShow()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}