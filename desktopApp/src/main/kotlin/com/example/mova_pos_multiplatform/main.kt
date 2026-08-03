package com.example.mova_pos_multiplatform

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.example.mova_pos_multiplatform.core.di.initKoin
import com.example.mova_pos_multiplatform.root.DefaultRootComponent

fun main() {
    initKoin()

    val lifecycle = LifecycleRegistry()

    val root = DefaultRootComponent(
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
    )

    application {
        val windowState = rememberWindowState()

        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "MOVA POS",
        ) {
            App(root)
        }
    }
}