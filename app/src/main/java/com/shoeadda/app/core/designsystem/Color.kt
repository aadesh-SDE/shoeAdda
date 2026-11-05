package com.shoeadda.app.core.designsystem

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

// Cocoa and Mist palette (see FEATURES.md and design/brand/color-options.html).
// Tan and mist blue are for backgrounds and decoration only, never for text or icons.

// Light
private val LightBackground = Color(0xFFFBFBFB)
private val LightSurface = Color(0xFFEEEEEC)
private val LightText = Color(0xFF131212)
private val LightCocoa = Color(0xFF68513F)
private val LightMuted = Color(0xFF6B6B66)
private val LightBorder = Color(0xFFD4D5D2)
private val LightSale = Color(0xFFB4472F)
private val LightMist = Color(0xFF9BB3BE)
private val LightTan = Color(0xFFA5967C)

// Dark
private val DarkBackground = Color(0xFF131212)
private val DarkSurface = Color(0xFF1F1E1E)
private val DarkText = Color(0xFFF2F0EC)
private val DarkCocoa = Color(0xFFC9B79A)
private val DarkMuted = Color(0xFFA19C94)
private val DarkBorder = Color(0xFF302E2E)
private val DarkSale = Color(0xFFE58A72)
private val DarkMist = Color(0xFF2B3A41)

/** The logo background. It stays the same in light and dark mode. */
val BrandCocoa = Color(0xFF68513F)

/** Material 3 roles filled from the Cocoa and Mist palette. Dynamic color is not used. */
internal val LightColorScheme: ColorScheme = lightColorScheme(
    primary = LightCocoa,
    onPrimary = Color.White,
    primaryContainer = LightSurface,
    onPrimaryContainer = LightText,
    secondary = LightCocoa,
    onSecondary = Color.White,
    secondaryContainer = LightSurface,
    onSecondaryContainer = LightText,
    tertiary = LightTan,
    onTertiary = LightText,
    background = LightBackground,
    onBackground = LightText,
    surface = LightBackground,
    onSurface = LightText,
    surfaceVariant = LightSurface,
    onSurfaceVariant = LightMuted,
    surfaceContainerLowest = LightBackground,
    surfaceContainerLow = LightBackground,
    surfaceContainer = LightSurface,
    surfaceContainerHigh = LightSurface,
    surfaceContainerHighest = LightSurface,
    outline = LightBorder,
    outlineVariant = LightBorder,
    error = LightSale,
    onError = Color.White,
)

internal val DarkColorScheme: ColorScheme = darkColorScheme(
    primary = DarkCocoa,
    onPrimary = DarkBackground,
    primaryContainer = DarkSurface,
    onPrimaryContainer = DarkText,
    secondary = DarkCocoa,
    onSecondary = DarkBackground,
    secondaryContainer = DarkSurface,
    onSecondaryContainer = DarkText,
    tertiary = DarkCocoa,
    onTertiary = DarkBackground,
    background = DarkBackground,
    onBackground = DarkText,
    surface = DarkBackground,
    onSurface = DarkText,
    surfaceVariant = DarkSurface,
    onSurfaceVariant = DarkMuted,
    surfaceContainerLowest = DarkBackground,
    surfaceContainerLow = DarkBackground,
    surfaceContainer = DarkSurface,
    surfaceContainerHigh = DarkSurface,
    surfaceContainerHighest = DarkSurface,
    outline = DarkBorder,
    outlineVariant = DarkBorder,
    error = DarkSale,
    onError = DarkBackground,
)

/** Colors from the design that Material 3 has no role for. Read them with [ShoeAddaTheme.extraColors]. */
@Immutable
data class ShoeAddaExtraColors(
    /** Backdrop behind product photos. Decoration only. */
    val photoBackdrop: Color,
    /** Sale price, discount tag. */
    val sale: Color,
    /** Highlight for badges. Decoration only. */
    val highlight: Color,
    /** Warm backdrop for illustrations (tan in light mode, dark brown in dark mode). Decoration only. */
    val warmBackdrop: Color,
)

internal val LightExtraColors = ShoeAddaExtraColors(
    photoBackdrop = LightMist,
    sale = LightSale,
    highlight = LightTan,
    warmBackdrop = LightTan,
)

internal val DarkExtraColors = ShoeAddaExtraColors(
    photoBackdrop = DarkMist,
    sale = DarkSale,
    highlight = DarkCocoa,
    warmBackdrop = Color(0xFF3E382F),
)
