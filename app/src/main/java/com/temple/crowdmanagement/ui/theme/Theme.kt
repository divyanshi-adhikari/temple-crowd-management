package com.temple.crowdmanagement.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary          = SaffronPrimary,
    onPrimary        = Color.Black,
    primaryContainer = ElevatedSurface,
    secondary        = SandstoneGold,
    onSecondary      = Color.Black,
    tertiary         = StatusGreen,
    onTertiary       = Color.Black,
    background       = SpiritualDarkBg,
    onBackground     = TextPrimary,
    surface          = CardDarkBg,
    onSurface        = TextPrimary,
    surfaceVariant   = SurfaceVariantDark,
    onSurfaceVariant = TextSecondary,
    error            = StatusRed,
    onError          = Color.White,
    outline          = SurfaceVariantDark
)

@Composable
fun TempleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            window?.let {
                // Transparent status bar — match the deep black top
                it.statusBarColor = SpiritualDarkBg.toArgb()
                WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = false
                // Also match nav bar
                it.navigationBarColor = SpiritualDarkBg.toArgb()
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content
    )
}