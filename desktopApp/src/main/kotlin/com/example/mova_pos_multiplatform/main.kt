package com.example.mova_pos_multiplatform

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.mova_pos_multiplatform.core.di.initKoin

fun main() = application {
    initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Mova_pos_multiplatform",
    ) {
        App()
    }
}