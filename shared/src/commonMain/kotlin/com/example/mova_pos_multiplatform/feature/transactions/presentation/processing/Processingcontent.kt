package com.example.mova_pos_multiplatform.feature.transactions.presentation.processing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.mova_pos_multiplatform.core.designsystem.MovaResponsive
import com.example.mova_pos_multiplatform.core.designsystem.MovaSize
import com.example.mova_pos_multiplatform.core.designsystem.MovaSpacing
import com.example.mova_pos_multiplatform.core.designsystem.MovaStatusColors
import com.example.mova_pos_multiplatform.core.designsystem.components.MovaLoadingIndicator
import com.example.mova_pos_multiplatform.core.designsystem.components.MovaPrimaryButton
import com.example.mova_pos_multiplatform.core.designsystem.components.MovaScreenScaffold

@Composable
fun ProcessingContent(component: ProcessingComponent) {
    val model by component.model.subscribeAsState()

    MovaResponsive { windowSize ->
        MovaScreenScaffold(windowSize = windowSize, title = "Procesando") {
            Box(
                modifier = Modifier.fillMaxSize().padding(MovaSpacing.lg),
                contentAlignment = Alignment.Center,
            ) {
                when (val state = model) {
                    is ProcessingComponent.Model.ReadingChannel -> LoadingState(
                        message = "Acerque la tarjeta o escanee el QR...",
                    )

                    is ProcessingComponent.Model.CreatingTransaction -> LoadingState(
                        message = "Guardando transacción...",
                    )

                    is ProcessingComponent.Model.Success -> SuccessState(message = state.message)

                    is ProcessingComponent.Model.Error -> ErrorState(
                        message = state.message,
                        onRetryClicked = component::onRetryClicked,
                        onCancelClicked = component::onCancelClicked,
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingState(message: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MovaSpacing.md),
    ) {
        MovaLoadingIndicator()
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun SuccessState(message: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MovaSpacing.md),
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = MovaStatusColors.completed,
            modifier = Modifier.size(MovaSize.iconLarge * 1.5f),
        )
        Text(
            text = message,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ErrorState(
    message: String,
    onRetryClicked: () -> Unit,
    onCancelClicked: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MovaSpacing.lg),
    ) {
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = null,
            tint = MovaStatusColors.failed,
            modifier = Modifier.size(MovaSize.iconLarge * 1.5f),
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        MovaPrimaryButton(text = "Reintentar", onClick = onRetryClicked)
        TextButton(onClick = onCancelClicked) {
            Text("Cancelar operación")
        }
    }
}