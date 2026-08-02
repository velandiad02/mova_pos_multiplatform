package com.example.mova_pos_multiplatform.core.designsystem.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mova_pos_multiplatform.core.designsystem.MovaSize

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
            .fillMaxWidth()
            .height(MovaSize.buttonHeight),
    ) {
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.height(20.dp))
        } else {
            Text(text)
        }
    }
}