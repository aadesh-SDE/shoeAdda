package com.shoeadda.app.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle

private val LocalExtraColors = staticCompositionLocalOf { LightExtraColors }
private val LocalPriceStyle = staticCompositionLocalOf { priceTextStyle(Script.Latin) }

/**
 * App theme: Material 3 with the Cocoa and Mist colors, Barlow Condensed and Archivo
 * (Noto Sans for Indian scripts), and M3 shapes. Light and dark follow the system setting.
 */
@Composable
fun ShoeAddaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val language = LocalConfiguration.current.locales[0]?.language.orEmpty()
    val script = scriptForLanguage(language)

    CompositionLocalProvider(
        LocalExtraColors provides if (darkTheme) DarkExtraColors else LightExtraColors,
        LocalPriceStyle provides priceTextStyle(script),
    ) {
        MaterialTheme(
            colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
            typography = shoeAddaTypography(script),
            shapes = ShoeAddaShapes,
            content = content,
        )
    }
}

/** Access to the design values that Material 3 has no slot for. */
object ShoeAddaTheme {
    val extraColors: ShoeAddaExtraColors
        @Composable @ReadOnlyComposable
        get() = LocalExtraColors.current

    val priceStyle: TextStyle
        @Composable @ReadOnlyComposable
        get() = LocalPriceStyle.current
}
