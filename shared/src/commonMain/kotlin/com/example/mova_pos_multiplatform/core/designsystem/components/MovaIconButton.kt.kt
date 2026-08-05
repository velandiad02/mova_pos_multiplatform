package com.example.mova_pos_multiplatform.core.designsystem.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MovaIconButton(
    onClick: () -> Unit,
    isLoading: Boolean,
    imageVector: ImageVector,
    contentDescription: String,
    tint: Color,
) {
    IconButton(
        onClick = onClick,
        enabled = !isLoading,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(size = 24.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 2.dp,
            )
        } else {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = tint
            )
        }
    }
}