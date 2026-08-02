package com.example.mova_pos_multiplatform

import androidx.compose.runtime.Composable

import com.example.mova_pos_multiplatform.core.designsystem.MovaTheme
import com.example.mova_pos_multiplatform.root.RootComponent
import com.example.mova_pos_multiplatform.root.RootContent

@Composable
fun App(root: RootComponent) {
    MovaTheme {
        RootContent(root)
    }
}
