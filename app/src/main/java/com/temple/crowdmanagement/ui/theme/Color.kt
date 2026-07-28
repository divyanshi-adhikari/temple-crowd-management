package com.temple.crowdmanagement.ui.theme

import androidx.compose.ui.graphics.Color

// =========== SOMNATH TEMPLE — DARK LUXURY PALETTE ===========
// Inspired by: Deep cosmic night sky + divine golden sanctum

// --- Backgrounds (Deep Navy-Black) ---
val SpiritualDarkBg      = Color(0xFF0D0B14)   // Near-black with purple depth — main screen bg
val CardDarkBg           = Color(0xFF1C1732)   // Deep purple-navy — card surfaces
val SurfaceVariantDark   = Color(0xFF261F3A)   // Mid purple — inner row highlights
val ElevatedSurface      = Color(0xFF2D2548)   // Elevated cards / dialogs

// --- Primary Gold / Saffron Palette ---
val SaffronPrimary       = Color(0xFFF5A623)   // Warm amber saffron — buttons, active elements
val SaffronLight         = Color(0xFFFFBF5C)   // Light amber glow
val SaffronDark          = Color(0xFFB87D1C)   // Deep amber — pressed states

// --- Gold Accents ---
val SandstoneGold        = Color(0xFFD4AF37)   // Classic gold — secondary labels, icons
val GoldAccent           = Color(0xFFE8C96D)   // Bright warm gold — heading text, tokens
val GoldMuted            = Color(0xFF9A7B2F)   // Muted gold — tertiary info

// --- Text ---
val TextPrimary          = Color(0xFFFFFFFF)   // Pure white — primary body text
val TextSecondary        = Color(0xFF9A90B4)   // Muted lavender-grey — subtitles
val TextTertiary         = Color(0xFF5E5578)   // Very muted — footnotes

// --- Status / Semantic ---
val StatusGreen          = Color(0xFF00C97A)   // Vibrant mint green — available, low crowd
val SuccessGreen         = StatusGreen
val StatusRed            = Color(0xFFFF3B5C)   // Vivid rose-red — emergency, full
val DangerRed            = StatusRed
val TempleRed            = StatusRed
val StatusOrange         = Color(0xFFF5A623)   // Same as Saffron — moderate/warnings
val WarningYellow        = Color(0xFFFFC107)   // Yellow — caution
val DivineWhite          = Color(0xFFFFF8E7)   // Warm white — divine highlights

// --- Legacy object (kept for compatibility) ---
object Dwarkadhish {
    val Primary  = SaffronPrimary
    val Light    = SaffronLight
    val Dark     = SaffronDark
    val Gold     = SandstoneGold
    val DarkBg   = SpiritualDarkBg
    val TextSec  = TextSecondary
}