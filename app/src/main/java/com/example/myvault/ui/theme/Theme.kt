package com.example.myvault.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Entregable 1: solo modo claro
private val LightColorScheme = lightColorScheme(
    primary          = PrimaryBlue,
    onPrimary        = SurfaceWhite,
    primaryContainer = PrimaryBlueLight,
    onPrimaryContainer = TextPrimary,
    secondary        = TextSecondary,
    onSecondary      = SurfaceWhite,
    tertiary         = SuccessGreen,
    background       = BackgroundGray,
    onBackground     = TextPrimary,
    surface          = SurfaceWhite,
    onSurface        = TextPrimary,
    surfaceVariant   = BackgroundGray,
    onSurfaceVariant = TextSecondary,
    outline          = BorderGray
)

@Composable
fun MyVaultTheme(content: @Composable () -> Unit) {
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = BackgroundGray.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography  = Typography,
        content     = content
    )
}
