package com.shoeadda.app.feature.start.domain.repository

/** Talks to the operating system about languages. Kept as an interface so the domain layer has no Android classes. */
interface LocaleController {
    /** Switches the app to [languageCode] (for example "hi"). The screen is redrawn in that language. */
    suspend fun applyLanguage(languageCode: String)

    /** The language code of the phone's own language setting (for example "mr"). */
    fun deviceLanguageCode(): String
}
