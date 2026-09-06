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

// ✅ REMOVED color definitions - they exist elsewhere
// Just use the existing colors directly

private val CustomColorScheme = darkColorScheme(
    primary          = SaffronPrimary,          // Uses existing definition
    onPrimary        = Color.White,
    primaryContainer = ElevatedSurface,
    secondary        = GoldAccent,
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
                it.statusBarColor = SaffronPrimary.toArgb()
                val insetsController = WindowCompat.getInsetsController(it, view)
                insetsController.isAppearanceLightStatusBars = true
                it.navigationBarColor = SpiritualDarkBg.toArgb()
                insetsController.isAppearanceLightNavigationBars = true
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content
    )
}