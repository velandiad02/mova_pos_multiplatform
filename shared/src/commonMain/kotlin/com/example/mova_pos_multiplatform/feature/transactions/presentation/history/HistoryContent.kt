package com.example.mova_pos_multiplatform.feature.transactions.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.mova_pos_multiplatform.core.designsystem.MovaResponsive
import com.example.mova_pos_multiplatform.core.designsystem.MovaSpacing
import com.example.mova_pos_multiplatform.core.designsystem.components.MovaIconButton
import com.example.mova_pos_multiplatform.core.designsystem.components.MovaScreenScaffold

@Composable
fun HistoryContent(component: HistoryComponent) {
    val model by component.model.subscribeAsState()

    MovaResponsive { windowSize ->
        MovaScreenScaffold(
            windowSize = windowSize,
            title = "Historial",
            actions = {
                if (model.hasFailedTransactions) {
                    MovaIconButton(
                        onClick = component::onRetrySyncClicked,
                        isLoading = model.isSyncing,
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reintentar Sincronización",
                        tint = MaterialTheme.colorScheme.error,
                    )
                }
            },
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(space = MovaSpacing.md),
            ) {
                model.errorMessage?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                if (model.transactions.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "No hay transacciones registradas",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(space = MovaSpacing.sm),
                    ) {
                        items(
                            items = model.transactions,
                            key = { it.idempotencyKey },
                        ) { transaction ->
                            TransactionItem(
                                transaction = transaction,
                                onItemClicked = {
                                    component.onTransactionClicked(transaction)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}