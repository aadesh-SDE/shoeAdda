package com.shoeadda.app.feature.start.domain.model

/** A language the app can be shown in. Names are shown in their own script, so they are not translated. */
data class AppLanguage(
    val code: String,
    val nativeName: String,
    val englishName: String,
)

object SupportedLanguages {
    const val DEFAULT_CODE = "en"

    val all: List<AppLanguage> = listOf(
        AppLanguage("en", "English", "English"),
        AppLanguage("hi", "हिन्दी", "Hindi"),
        AppLanguage("mr", "मराठी", "Marathi"),
        AppLanguage("gu", "ગુજરાતી", "Gujarati"),
        AppLanguage("ta", "தமிழ்", "Tamil"),
        AppLanguage("te", "తెలుగు", "Telugu"),
    )

    fun isSupported(code: String): Boolean = all.any { it.code == code }
}
