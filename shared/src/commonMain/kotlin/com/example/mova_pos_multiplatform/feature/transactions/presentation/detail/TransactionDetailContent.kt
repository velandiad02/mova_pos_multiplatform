package com.example.mova_pos_multiplatform.feature.transactions.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.mova_pos_multiplatform.core.designsystem.MovaResponsive
import com.example.mova_pos_multiplatform.core.designsystem.MovaSpacing
import com.example.mova_pos_multiplatform.core.designsystem.MovaStatusColors
import com.example.mova_pos_multiplatform.core.designsystem.components.MovaPrimaryButton
import com.example.mova_pos_multiplatform.core.designsystem.components.MovaScreenScaffold
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.TransactionStatus

@Composable
fun TransactionDetailContent(component: TransactionDetailComponent) {
    val model by component.model.subscribeAsState()

    MovaResponsive { windowSize ->
        MovaScreenScaffold(
            windowSize = windowSize,
            title = "Detalle de Transacción",
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (model.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    model.transaction?.let { transaction ->
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(MovaSpacing.md),
                        ) {
                            TransactionInfoCard(transaction = transaction)

                            Spacer(modifier = Modifier.weight(1f))

                            model.printerMessage?.let { message ->
                                Text(
                                    text = message,
                                    color = if (model.isPrintError)
                                        MaterialTheme.colorScheme.error
                                    else
                                        MovaStatusColors.completed,
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            }

                            if (transaction.status == TransactionStatus.APPROVED) {
                                MovaPrimaryButton(
                                    text = "Imprimir Recibo",
                                    onClick = component::onPrintReceiptClicked,
                                    loading = model.isPrinting,
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}