package com.example.mova_pos_multiplatform.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


private val MovaPrimary = Color(0xFF1E5AFF)
private val MovaPrimaryDark = Color(0xFF3D7CFF)

private val MovaSuccess = Color(0xFF1FAA59)
private val MovaWarning = Color(0xFFF2A93B)
private val MovaError = Color(0xFFE0453C)

object MovaStatusColors {
    val completed = MovaSuccess
    val pendingSync = MovaWarning
    val failed = MovaError

}

private val LightColors = lightColorScheme(
    primary = MovaPrimary,
    secondary = MovaSuccess,
    error = MovaError,
)

private val DarkColors = darkColorScheme(
    primary = MovaPrimaryDark,
    secondary = MovaSuccess,
    error = MovaError,
)

private val MovaTypography = Typography(
    headlineLarge = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold),
    headlineMedium = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.SemiBold),
    titleLarge = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold),
    bodyLarge = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal),
    bodyMedium = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal),
    labelLarge = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium),
)

@Composable
fun MovaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = MovaTypography,
        content = content
    )
}
