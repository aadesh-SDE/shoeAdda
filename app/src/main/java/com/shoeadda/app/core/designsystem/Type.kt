package com.shoeadda.app.core.designsystem

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.shoeadda.app.R

/** Writing systems the app is shown in. Each one gets its own font family. */
enum class Script { Latin, Devanagari, Gujarati, Tamil, Telugu }

/** Hindi and Marathi share Devanagari. Everything else not listed uses Latin. */
fun scriptForLanguage(languageCode: String): Script = when (languageCode) {
    "hi", "mr" -> Script.Devanagari
    "gu" -> Script.Gujarati
    "ta" -> Script.Tamil
    "te" -> Script.Telugu
    else -> Script.Latin
}

// Variable fonts: one file, several weights. Works on API 26 and up.
private fun variableFamily(resId: Int): FontFamily = FontFamily(
    listOf(
        FontWeight.Normal,
        FontWeight.Medium,
        FontWeight.SemiBold,
        FontWeight.Bold,
    ).map { weight ->
        Font(
            resId = resId,
            weight = weight,
            variationSettings = FontVariation.Settings(FontVariation.weight(weight.weight)),
        )
    },
)

/** The headline font. Also used for the ShoeAdda wordmark, which is always shown in Latin letters. */
val BarlowCondensed = FontFamily(
    Font(R.font.barlow_condensed_semibold, FontWeight.SemiBold),
    Font(R.font.barlow_condensed_bold, FontWeight.Bold),
)
private val Archivo = variableFamily(R.font.archivo)
private val NotoDevanagari = variableFamily(R.font.noto_sans_devanagari)
private val NotoGujarati = variableFamily(R.font.noto_sans_gujarati)
private val NotoTamil = variableFamily(R.font.noto_sans_tamil)
private val NotoTelugu = variableFamily(R.font.noto_sans_telugu)

private fun headlineFamily(script: Script): FontFamily = when (script) {
    Script.Latin -> BarlowCondensed
    Script.Devanagari -> NotoDevanagari
    Script.Gujarati -> NotoGujarati
    Script.Tamil -> NotoTamil
    Script.Telugu -> NotoTelugu
}

private fun bodyFamily(script: Script): FontFamily = when (script) {
    Script.Latin -> Archivo
    Script.Devanagari -> NotoDevanagari
    Script.Gujarati -> NotoGujarati
    Script.Tamil -> NotoTamil
    Script.Telugu -> NotoTelugu
}

/** Font family for text that must be shown in a specific language's script, such as a language's own name. */
fun fontFamilyForLanguage(languageCode: String): FontFamily = bodyFamily(scriptForLanguage(languageCode))

/**
 * English headlines are uppercase in the design. Indian scripts have no uppercase, so they are left as they are.
 */
@Composable
@ReadOnlyComposable
fun String.asHeadline(): String {
    val language = LocalConfiguration.current.locales[0]?.language.orEmpty()
    return if (scriptForLanguage(language) == Script.Latin) uppercase() else this
}

/**
 * Material 3 type scale using the design's sizes: display 36, title 22, body 15, caption 12.
 * Headlines use Barlow Condensed in English. Indian scripts use their Noto Sans family everywhere.
 * Uppercase for English headlines is applied where the text is shown, because Indian scripts have no uppercase.
 */
fun shoeAddaTypography(script: Script): Typography {
    val head = headlineFamily(script)
    val body = bodyFamily(script)
    val headTracking = if (script == Script.Latin) 0.01.em else 0.em
    return Typography(
        displayLarge = TextStyle(fontFamily = head, fontWeight = FontWeight.Bold, fontSize = 36.sp, lineHeight = 42.sp, letterSpacing = headTracking),
        displayMedium = TextStyle(fontFamily = head, fontWeight = FontWeight.Bold, fontSize = 32.sp, lineHeight = 38.sp, letterSpacing = headTracking),
        displaySmall = TextStyle(fontFamily = head, fontWeight = FontWeight.Bold, fontSize = 28.sp, lineHeight = 34.sp, letterSpacing = headTracking),
        headlineLarge = TextStyle(fontFamily = head, fontWeight = FontWeight.Bold, fontSize = 26.sp, lineHeight = 32.sp, letterSpacing = headTracking),
        headlineMedium = TextStyle(fontFamily = head, fontWeight = FontWeight.Bold, fontSize = 24.sp, lineHeight = 30.sp, letterSpacing = headTracking),
        headlineSmall = TextStyle(fontFamily = head, fontWeight = FontWeight.Bold, fontSize = 22.sp, lineHeight = 28.sp, letterSpacing = headTracking),
        titleLarge = TextStyle(fontFamily = head, fontWeight = FontWeight.Bold, fontSize = 22.sp, lineHeight = 28.sp, letterSpacing = headTracking),
        titleMedium = TextStyle(fontFamily = body, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, lineHeight = 24.sp),
        titleSmall = TextStyle(fontFamily = body, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, lineHeight = 20.sp),
        bodyLarge = TextStyle(fontFamily = body, fontWeight = FontWeight.Normal, fontSize = 15.sp, lineHeight = 22.sp),
        bodyMedium = TextStyle(fontFamily = body, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp),
        bodySmall = TextStyle(fontFamily = body, fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 16.sp),
        labelLarge = TextStyle(fontFamily = body, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, lineHeight = 20.sp),
        labelMedium = TextStyle(fontFamily = body, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, lineHeight = 16.sp),
        labelSmall = TextStyle(fontFamily = body, fontWeight = FontWeight.SemiBold, fontSize = 11.sp, lineHeight = 16.sp),
    )
}

/** Price text: 20sp bold with digits of equal width so prices line up. */
fun priceTextStyle(script: Script): TextStyle = TextStyle(
    fontFamily = bodyFamily(script),
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp,
    lineHeight = 26.sp,
    fontFeatureSettings = "tnum",
)
