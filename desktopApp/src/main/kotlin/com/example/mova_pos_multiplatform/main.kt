package com.example.mova_pos_multiplatform

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Mova_pos_multiplatform",
    ) {
        App()
    }
}