package com.example.mova_pos_multiplatform.core.designsystem.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mova_pos_multiplatform.core.designsystem.MovaSize
import com.example.mova_pos_multiplatform.core.designsystem.MovaSpacing

@Composable
fun MovaPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
) {
    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        modifier = modifier
            .height(MovaSize.buttonHeight),
        shape = RoundedCornerShape(12.dp),
    ) {
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.height(MovaSpacing.md))
        } else {
            Text(text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                )
        }
    }
}