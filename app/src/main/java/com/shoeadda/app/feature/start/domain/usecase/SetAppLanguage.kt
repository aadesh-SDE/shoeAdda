package com.shoeadda.app.feature.start.domain.usecase

import com.shoeadda.app.feature.start.domain.model.SupportedLanguages
import com.shoeadda.app.feature.start.domain.repository.StartRepository
import javax.inject.Inject

class SetAppLanguage @Inject constructor(
    private val repository: StartRepository,
) {
    /** Saves and applies the language. A code that is not one of the six languages is ignored. */
    suspend operator fun invoke(languageCode: String) {
        if (SupportedLanguages.isSupported(languageCode)) {
            repository.setLanguage(languageCode)
        }
    }
}
