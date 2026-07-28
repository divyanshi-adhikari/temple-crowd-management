package com.temple.crowdmanagement.ui.theme

import androidx.compose.ui.graphics.Color

// ── SOMNATH TEMPLE  |  WARM LIGHT / SLATE HYBRID PALETTE ─────────
// Inspired by: Serene sand/cream temples, rich heritage maroon & modern slate cards.
// Move away from neon dark mode to a premium, warm light hybrid interface.

// ── Backgrounds (Warm Sand & Cream) ─────────────────────────────
val SpiritualDarkBg    = Color(0xFFF7F4EB)   // Warm cream/sand background (replaces dark bg)
val CardDarkBg         = Color(0xFFFFFFFF)   // Pure white card surfaces
val SurfaceVariantDark = Color(0xFFEAE8E3)   // Light slate/sand pill background
val ElevatedSurface    = Color(0xFFFFFFFF)   // White elevated surface / dialogs

// ── Brand — Heritage Maroon (Top bar & primary CTA) ─────────────
val SaffronPrimary     = Color(0xFF7A1E29)   // Rich heritage temple maroon (primary brand)
val SaffronLight       = Color(0xFF9E2C3A)   // Lighter maroon tint
val SaffronDark        = Color(0xFF5E151E)   // Deep maroon for status bars / shadows

// ── Accent Slate (Heatmap & secondary actions) ──────────────────
val AccentSlate        = Color(0xFF2F3E46)   // Slate gray-blue for actions & headers

// ── Accent — Warm Gold / Amber ──────────────────────────────────
val GoldAccent         = Color(0xFFB8860B)   // Dark goldenrod / gold highlights
val SandstoneGold      = Color(0xFFC59B3F)   // Soft gold accent
val GoldMuted          = Color(0xFF8A6E35)   // Low-emphasis gold

// ── Text (Highly legible on light backgrounds) ──────────────────
val TextPrimary        = Color(0xFF2D2A32)   // Deep charcoal text
val TextSecondary      = Color(0xFF6B6675)   // Muted slate gray text
val TextTertiary       = Color(0xFF9E9AAB)   // Low-emphasis text

// ── Status ───────────────────────────────────────────────────────
val StatusGreen        = Color(0xFF16A34A)   // Rich emerald green (open / low crowd)
val SuccessGreen       = StatusGreen
val StatusRed          = Color(0xFFDC2626)   // Vivid red (emergency / heavy crowd)
val DangerRed          = StatusRed
val TempleRed          = StatusRed
val StatusOrange       = Color(0xFFD97706)   // Warm amber orange (moderate)
val WarningYellow      = StatusOrange
val DivineWhite        = Color(0xFFFFF8E7)   // Divine highlight white

// ── Legacy alias (compatibility) ────────────────────────────────
object Dwarkadhish {
    val Primary = SaffronPrimary
    val Light   = SaffronLight
    val Dark    = SaffronDark
    val Gold    = SandstoneGold
    val DarkBg  = SpiritualDarkBg
    val TextSec = TextSecondary
}