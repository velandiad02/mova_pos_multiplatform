package com.example.mova_pos_multiplatform.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import com.example.mova_pos_multiplatform.core.designsystem.MovaBorder
import com.example.mova_pos_multiplatform.core.designsystem.MovaOpacity
import com.example.mova_pos_multiplatform.core.designsystem.MovaRadius
import com.example.mova_pos_multiplatform.core.designsystem.MovaSize
import com.example.mova_pos_multiplatform.core.designsystem.MovaSpacing

@Composable
fun MovaAmountField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Monto a cobrar",
    modifier: Modifier = Modifier,
) {
    var isFocused by remember { mutableStateOf(false) }

    val fieldShape = RoundedCornerShape(MovaRadius.medium)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(MovaSize.buttonHeight)
            .clip(fieldShape)
            .background(
                if (isFocused) {
                    MaterialTheme.colorScheme.primary.copy(alpha = MovaOpacity.selectedTint)
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                }
            )
            .border(
                width = if (isFocused) MovaBorder.focused else MovaBorder.idle,
                color = if (isFocused) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = MovaOpacity.borderIdle)
                },
                shape = fieldShape,
            )
            .padding(horizontal = MovaSpacing.md),
        contentAlignment = Alignment.CenterStart,
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = if (value.isNotEmpty()) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(
                        alpha = MovaOpacity.disabledContent,
                    )
                },
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
            ),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = MovaOpacity.disabledContent,
                            ),
                        )
                    }

                    innerTextField()
                }
            },
        )
    }
}