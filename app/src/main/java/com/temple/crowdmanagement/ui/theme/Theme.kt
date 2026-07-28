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

private val CustomColorScheme = darkColorScheme(
    primary          = SaffronPrimary,          // Heritage maroon
    onPrimary        = Color.White,
    primaryContainer = ElevatedSurface,
    secondary        = GoldAccent,              // Warm gold
    onSecondary      = Color.White,
    tertiary         = StatusGreen,
    onTertiary       = Color.White,
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
    val colorScheme = CustomColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            window?.let {
                // Status bar matches the maroon top bar
                it.statusBarColor = SaffronDark.toArgb()
                val insetsController = WindowCompat.getInsetsController(it, view)
                insetsController.isAppearanceLightStatusBars = false // White text/icons on dark maroon status bar
                
                // Navigation bar matches the warm sand/cream bg
                it.navigationBarColor = SpiritualDarkBg.toArgb()
                insetsController.isAppearanceLightNavigationBars = true // Dark icons on light sand nav bar
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content
    )
}