package com.example.mova_pos_multiplatform.core.designsystem

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class MovaWindowSize {
    Compact,
    Medium,
    Expanded;

    val isCompact get() = this == Compact
    val isExpandedOrWider get() = this == Expanded

    companion object {
        fun fromWidth(width: Dp): MovaWindowSize = when {
            width < 600.dp -> Compact
            width < 900.dp -> Medium
            else -> Expanded
        }
    }
}

@Composable
fun MovaResponsive(
    modifier: Modifier = Modifier,
    content: @Composable (windowSize: MovaWindowSize) -> Unit
) {
    BoxWithConstraints(modifier = modifier) {
        val windowSize = MovaWindowSize.fromWidth(maxWidth)
        content(windowSize)
    }
}