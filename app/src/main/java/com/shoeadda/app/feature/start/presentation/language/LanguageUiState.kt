package com.shoeadda.app.feature.start.presentation.language

import com.shoeadda.app.feature.start.domain.model.AppLanguage

data class LanguageUiState(
    val languages: List<AppLanguage> = emptyList(),
    /** Empty until the saved choice (or the phone's language) has been read. */
    val selectedCode: String = "",
)

sealed interface LanguageEvent {
    /** The choice is saved. Go to the next screen. */
    data object Continue : LanguageEvent
}
