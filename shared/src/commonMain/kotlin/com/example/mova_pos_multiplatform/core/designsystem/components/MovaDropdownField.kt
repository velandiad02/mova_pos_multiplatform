package com.example.mova_pos_multiplatform.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.mova_pos_multiplatform.core.designsystem.MovaBorder
import com.example.mova_pos_multiplatform.core.designsystem.MovaOpacity
import com.example.mova_pos_multiplatform.core.designsystem.MovaRadius
import com.example.mova_pos_multiplatform.core.designsystem.MovaSize
import com.example.mova_pos_multiplatform.core.designsystem.MovaSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> MovaDropdownField(
    label: String,
    options: List<T>,
    selected: T?,
    onSelected: (T) -> Unit,
    optionLabel: (T) -> String,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    placeholder: String = "Selecciona una opción",
) {
    var expanded by remember { mutableStateOf(false) }

    // Una sola forma para todo el componente: si el radio cambia en el theme,
    // este dropdown lo sigue automáticamente (fondo y borde usaban valores
    // distintos entre sí antes -- 12.dp suelto vs MovaRadius.medium).
    val fieldShape = RoundedCornerShape(MovaRadius.medium)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            if (!isLoading) expanded = it
        },
        modifier = modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .height(MovaSize.buttonHeight)
                .clip(fieldShape)
                .background(
                    if (expanded) {
                        MaterialTheme.colorScheme.primary.copy(alpha = MovaOpacity.selectedTint)
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }
                )
                .border(
                    width = if (expanded) MovaBorder.focused else MovaBorder.idle,
                    color = if (expanded) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = MovaOpacity.borderIdle)
                    },
                    shape = fieldShape,
                )
                .padding(horizontal = MovaSpacing.md),
            contentAlignment = Alignment.CenterStart,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (isLoading) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(MovaSize.iconSmall),
                                strokeWidth = MovaSize.strokeThin,
                                color = MaterialTheme.colorScheme.primary,
                            )
                            Text(
                                text = " Cargando...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = MovaOpacity.mutedContent),
                            )
                        }
                    } else {
                        Text(
                            text = selected?.let(optionLabel) ?: placeholder,
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (selected != null) {
                                MaterialTheme.colorScheme.onSurface
                            } else {
                                MaterialTheme.colorScheme.onSurface.copy(alpha = MovaOpacity.disabledContent)
                            },
                        )
                    }
                }

                if (selected != null && !isLoading) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Seleccionado",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(MovaSize.iconSmall),
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Abrir",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = MovaOpacity.disabledContent),
                        modifier = Modifier.size(MovaSize.iconMedium),
                    )
                }
            }
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            if (options.isEmpty()) {
                DropdownMenuItem(
                    text = {
                        Text(
                            "No hay opciones",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = MovaOpacity.disabledContent),
                        )
                    },
                    onClick = {},
                )
            } else {
                options.forEach { option ->
                    val isSelected = option == selected
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = optionLabel(option),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onSurface
                                    },
                                    modifier = Modifier.weight(1f),
                                )
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Seleccionado",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(MovaSize.iconSmall),
                                    )
                                }
                            }
                        },
                        onClick = {
                            onSelected(option)
                            expanded = false
                        },
                        modifier = Modifier.background(
                            if (isSelected) {
                                MaterialTheme.colorScheme.primary.copy(alpha = MovaOpacity.selectedTint)
                            } else {
                                Color.Transparent
                            }
                        ),
                    )
                }
            }
        }
    }
}