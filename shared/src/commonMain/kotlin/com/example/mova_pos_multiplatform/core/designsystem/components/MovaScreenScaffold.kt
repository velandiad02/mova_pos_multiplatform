package com.example.mova_pos_multiplatform.core.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.mova_pos_multiplatform.core.designsystem.MovaWindowSize
import com.example.mova_pos_multiplatform.core.designsystem.MovaSize
import com.example.mova_pos_multiplatform.core.designsystem.MovaSpacing


@Composable
fun MovaScreenScaffold(
    windowSize: MovaWindowSize,
    title: String? = null,
    modifier: Modifier = Modifier,
    onBackClicked: (() -> Unit)? = null,
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
                title = {
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                navigationIcon = {
                    if (onBackClicked != null) {
                        IconButton(onClick = onBackClicked) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver",
                            )
                        }
                    }
                },
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
