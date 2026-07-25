package com.temple.crowdmanagement.ui.theme

import androidx.compose.ui.graphics.Color

// =========== TEMPLE THEME COLORS ===========

// Primary Colors
val SaffronPrimary = Color(0xFFFF9933)      // Main Saffron
val SaffronLight = Color(0xFFFFB366)        // Light Saffron
val SaffronDark = Color(0xFFCC7A00)         // Dark Saffron

// Accent & Gold Colors
val SandstoneGold = Color(0xFFD4AF37)       // Gold
val GoldAccent = Color(0xFFFFD700)          // Bright Gold
val DivineWhite = Color(0xFFFFF8E7)         // Divine White

// Background & Card Colors
val SpiritualDarkBg = Color(0xFF1A0E0A)     // Dark Background
val CardDarkBg = Color(0xFF261813)          // Dark Card Background
val SurfaceVariantDark = Color(0xFF33221A)  // Dark Surface Variant

// Text Colors
val TextPrimary = Color(0xFFFFFFFF)         // White Text
val TextSecondary = Color(0xFFB8A99A)       // Secondary Muted Text

// Status & Emergency Colors
val SuccessGreen = Color(0xFF4CAF50)        // Green
val StatusGreen = SuccessGreen              // Alias for Dev 2
val WarningYellow = Color(0xFFFFC107)       // Yellow
val DangerRed = Color(0xFFF44336)           // Red
val StatusRed = DangerRed                   // Alias for Dev 2
val TempleRed = DangerRed                   // Fixes Theme.kt
val StatusOrange = Color(0xFFFF9800)        // Orange for Emergency Mesh Alerts

//  Dwarkadhish Theme Wrapper Object (Self-referential lines removed)
object Dwarkadhish {
    val Primary = SaffronPrimary
    val Light = SaffronLight
    val Dark = SaffronDark
    val Gold = SandstoneGold
    val DarkBg = SpiritualDarkBg
    val TextSec = TextSecondary
}