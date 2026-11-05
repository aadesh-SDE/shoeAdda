package com.shoeadda.app.feature.start.domain.model

/** What the app remembers about the first-run steps. */
data class StartState(
    /** The language the user chose, or null if they have not chosen one yet. */
    val languageCode: String?,
    val onboardingCompleted: Boolean,
)
