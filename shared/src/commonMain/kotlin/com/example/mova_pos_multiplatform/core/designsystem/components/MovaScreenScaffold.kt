package com.example.mova_pos_multiplatform.core.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mova_pos_multiplatform.core.designsystem.MovaWindowSize
import com.example.mova_pos_multiplatform.core.designsystem.MovaSize
import com.example.mova_pos_multiplatform.core.designsystem.MovaSpacing


@Composable
fun MovaScreenScaffold(
    windowSize: MovaWindowSize,
    title: String? = null,
    modifier: Modifier = Modifier,
    actions: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val maxContentWidth = if (windowSize.isExpandedOrWider) {
        MovaSize.contentMaxWidthExpanded
    } else {
        MovaSize.contentMaxWidthCompact
    }

    val topBar: @Composable () -> Unit = {
        if (title != null) {
            CenterAlignedTopAppBar(
                title = { Text(title) },
                actions = { actions() },
            )
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = topBar,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.TopCenter,
        ) {
            Box(
                modifier = Modifier
                    .widthIn(max = maxContentWidth)
                    .padding(MovaSpacing.md),
            ) {
                content()
            }
        }
    }
}
